package com.southwind.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2023-12-12
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Property implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "property_id", type = IdType.AUTO)
      private Integer propertyId;

    private String propertyCode;

    private String propertyName;

    private String contact;

    private String phone;

    private Integer status;

      @TableField(fill = FieldFill.INSERT)
      private Date createTime;


}
