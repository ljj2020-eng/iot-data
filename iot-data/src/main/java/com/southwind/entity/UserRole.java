package com.southwind.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户与角色对应关系
 * </p>
 *
 * @author admin
 * @since 2023-08-03
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class UserRole implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 用户ID
     */
        private Integer userId;

      /**
     * 角色ID
     */
      private Integer roleId;


}
