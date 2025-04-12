package com.southwind.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author admin
 * @since 2023-12-13
 */
@Data
    public class PayRecordVO implements Serializable {


      private Integer payRecordId;

    private String propertyName;

    private String parkName;

    private String number;

      private String payType;

    private Integer amount;

      private Date createTime;
}
