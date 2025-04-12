package com.southwind.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

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
    public class PropertyVO implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer propertyId;

    private String propertyCode;

    private String propertyName;

    private String contact;

    private String phone;

    private String status;

    private Date createTime;

}
