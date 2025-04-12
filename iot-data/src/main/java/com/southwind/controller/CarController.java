package com.southwind.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.annotation.LogAnnotation;
import com.southwind.entity.*;
import com.southwind.form.CarListForm;
import com.southwind.form.ParkListForm;
import com.southwind.form.PayForm;
import com.southwind.mapper.ParkMapper;
import com.southwind.service.CarService;
import com.southwind.service.ParkService;
import com.southwind.service.PropertyService;
import com.southwind.util.Result;
import com.southwind.vo.PayVO;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-12-13
 */
@RestController
@RequestMapping("/sys/car")
public class CarController {

    @Autowired
    private CarService carService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ParkService parkService;
    @Value("${upload.excel}")
    private String excel;
    @Autowired
    private ParkMapper parkMapper;

    @GetMapping("/list")
    public Result list(CarListForm carListForm){
        Map<String,Object> map = new HashMap<>();
        map.put("propertyList", this.propertyService.list());
        map.put("parkList",this.parkService.list());
        map.put("pageList",this.carService.carList(carListForm));
        return Result.ok().put("data", map);
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id){
        if(id == 0) {
            Map<String, List> map = new HashMap<>();
            map.put("propertyList", this.propertyService.list());
            map.put("parkList", this.parkService.list());
            return Result.ok().put("data",map);
        } else {
            Map<String,Object> map = new HashMap<>();
            map.put("propertyList", this.propertyService.list());
            map.put("parkList", this.parkService.list());
            map.put("car", this.carService.getById(id));
            return Result.ok().put("data", map);
        }
    }

    @GetMapping("/payInfo/{id}")
    public Result payInfo(@PathVariable("id") Integer id){
        Map<String,Object> map = new HashMap<>();
        map.put("propertyList", this.propertyService.list());
        map.put("parkList", this.parkService.list());
        Car car = this.carService.getById(id);
        map.put("car", car);
        Integer monthlyPrice = this.parkMapper.getMonthlyPrice(car.getParkId());
        List<PayVO> list = new ArrayList<>();
        list.add(new PayVO("1个月费用"+monthlyPrice+"元", 1));
        list.add(new PayVO("3个月费用"+(monthlyPrice*3-100)+"元", 3));
        list.add(new PayVO("6个月费用"+(monthlyPrice*6-300)+"元", 6));
        map.put("payList", list);
        return Result.ok().put("data", map);
    }

    @LogAnnotation("车辆续费")
    @PutMapping("/updatePay")
    public Result updatePay(@RequestBody PayForm payForm){
        Car car = this.carService.getById(payForm.getCarId());
        Date effectTime = car.getEffectTime();
        //未办理续费
        if(effectTime == null) {
            effectTime = new Date();
        }
        Instant instant = effectTime.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.plusMonths(payForm.getMonth());
        effectTime = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        car.setEffectTime(effectTime);
        boolean updateById = this.carService.updateById(car);
        if(!updateById) return Result.error("车辆续费失败");
        return Result.ok();
    }

    @LogAnnotation("添加车辆信息")
    @PostMapping("/add")
    public Result add(@RequestBody Car car){
        boolean save = this.carService.save(car);
        if(!save) return Result.error("车辆信息添加失败");
        return Result.ok();
    }

    @LogAnnotation("编辑车辆信息")
    @PutMapping("/edit")
    public Result edit(@RequestBody Car car){
        boolean updateById = this.carService.updateById(car);
        if(!updateById) return Result.error("车辆信息修改失败");
        return Result.ok();
    }

    @LogAnnotation("删除车辆信息")
    @DeleteMapping("/del")
    public Result del(@RequestBody Integer[] ids){
        boolean removeByIds = this.carService.removeByIds(Arrays.asList(ids));
        if(!removeByIds) return Result.error("车辆信息删除失败");
        return Result.ok();
    }

    @PostMapping("/excelUpload")
    public Result excelUpload(@RequestParam("uploadExcel") MultipartFile file) throws Exception {
        if(file.getOriginalFilename().equals("")){
            return Result.error("没有选中要上传的文件");
        } else {
            String picName = UUID.randomUUID().toString();
            String oriName = file.getOriginalFilename();
            String extName = oriName.substring(oriName.lastIndexOf("."));
            String newFileName = picName + extName;
            File targetFile = new File(excel, newFileName);
            // 保存文件
            file.transferTo(targetFile);
            return Result.ok().put("data",newFileName);
        }
    }

    @LogAnnotation("导入车辆信息")
    @PostMapping("/parsefile/{fileName}")
    public Result parsefile(@PathVariable("fileName") String fileName){
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        try {
            String basePath = excel + fileName;
            fs = new POIFSFileSystem(new FileInputStream(basePath));
            wb = new HSSFWorkbook(fs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HSSFSheet sheet = wb.getSheetAt(0);
        Object[][] data = null;
        int r = sheet.getLastRowNum()+1;
        int c = sheet.getRow(0).getLastCellNum();
        int headRow = 2;
        data = new Object[r - headRow][c];
        for (int i = headRow; i < r; i++) {
            HSSFRow row = sheet.getRow(i);
            for (int j = 0; j < c; j++) {
                HSSFCell cell = null;
                try {
                    cell = row.getCell(j);
                    try {
                        cell = row.getCell(j);
                        DataFormatter dataFormater = new DataFormatter();
                        String a = dataFormater.formatCellValue(cell);
                        data[i - headRow][j] = a;
                    } catch (Exception e) {
                        data[i-headRow][j] = "";
                        if(j==0){
                            try {
                                double d = cell.getNumericCellValue();
                                data[i - headRow][j] = (int)d + "";
                            }catch(Exception ex){
                                data[i-headRow][j] = "";
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("i="+i+";j="+j+":"+e.getMessage());
                }
            }
        }

        int row = data.length;
        int col = 0;
        String errinfo = "";
        headRow = 3;
        String[] stitle={"ID","公司名称","停车场","车牌号","车主姓名","性别","手机号","已购车位","备注"};
        errinfo = "";
        for (int i = 0; i < row; i++) {
            Car single = new Car();
            try {
                col=1;
                String propertyName = data[i][col++].toString();
                QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("property_name", propertyName);
                Property property = this.propertyService.getOne(queryWrapper);
                if( property == null){
                    errinfo += "Excel文件第" + (i + headRow) + "行公司不存在！";
                    return Result.ok().put("status", "fail").put("data", errinfo);
                }
                single.setPropertyId(property.getPropertyId());

                String parkName = data[i][col++].toString();
                QueryWrapper<Park> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("park_name", parkName);
                Park park = this.parkService.getOne(queryWrapper1);
                if( park == null){
                    errinfo += "Excel文件第" + (i + headRow) + "行停车场不存在！";
                    return Result.ok().put("status", "fail").put("data", errinfo);
                }
                single.setParkId(park.getParkId());
                single.setNumber(data[i][col++].toString());
                single.setName(data[i][col++].toString());
                single.setGender(data[i][col++].toString());
                single.setPhone(data[i][col++].toString());
                single.setSpace(data[i][col++].toString());
                single.setRemark(data[i][col++].toString());

                this.carService.save(single);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Result.ok().put("status", "success").put("data","数据导入完成！");
    }
}

