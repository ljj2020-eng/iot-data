package com.southwind.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2023-12-13
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class PayRecord implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "pay_record_id", type = IdType.AUTO)
      private Integer payRecordId;

    private Integer propertyId;

    private Integer parkId;

    private String number;

      /**
     * 1:临时车
2:包月车
3:VIP

     */
      private Integer payType;

    private Integer amount;

      @TableField(fill = FieldFill.INSERT)
      private Date createTime;


}
