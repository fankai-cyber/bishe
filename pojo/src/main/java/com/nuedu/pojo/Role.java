package com.nuedu.pojo;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author fankai
 * @since 2020-11-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends BasePojo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer active;


}
