package com.southwind.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色与菜单对应关系
 * </p>
 *
 * @author admin
 * @since 2023-08-03
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class RoleMenu implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 角色ID
     */
        private Integer roleId;

      /**
     * 菜单ID
     */
      private Integer menuId;


}
