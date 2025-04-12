package com.southwind.mapper;

import com.southwind.entity.InOutRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.southwind.vo.ChartVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2023-07-26
 */
public interface InOutRecordMapper extends BaseMapper<InOutRecord> {

    @Select({
            "select sum(amount) value,p.park_name name from park p,pay_record pr where p.park_id = pr.park_id group by pr.park_id"
    })
    public List<ChartVO> chart();

    @Select({
            "select * from in_out_record where " +
                    "park_id=#{parkId} and " +
                    "number=#{number} and out_time is null"
    })
    public InOutRecord getInOutRecord(InOutRecord inOutRecord);

}
