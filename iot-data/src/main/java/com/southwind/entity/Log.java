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
 * 操作日志表
 * </p>
 *
 * @author admin
 * @since 2023-08-04
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Log implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "log_id", type = IdType.AUTO)
      private Integer logId;

      /**
     * 用户名
     */
      private String username;

      /**
     * 用户操作
     */
      private String operation;

      /**
     * 请求方法
     */
      private String method;

      /**
     * 请求参数
     */
      private String params;

      /**
     * 执行时长(毫秒)
     */
      private Integer time;

      /**
     * IP地址
     */
      private String ip;

      /**
     * 创建时间
     */
        @TableField(fill = FieldFill.INSERT)
      private Date createTime;


}
