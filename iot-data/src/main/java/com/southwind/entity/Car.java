package com.southwind.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author admin
 * @since 2023-12-13
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Car implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "car_id", type = IdType.AUTO)
      private Integer carId;

    private Integer propertyId;

    private Integer parkId;

    private String number;

    private String name;

    private String gender;

    private String phone;

    private Date effectTime;

    private String space;

    private String remark;

}
