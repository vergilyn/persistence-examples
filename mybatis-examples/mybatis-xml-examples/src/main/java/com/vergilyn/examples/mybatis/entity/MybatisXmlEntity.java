package com.vergilyn.examples.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Generated;

import lombok.Data;

/**
 *
 * @author vergilyn
 * @since 2021-02-02
 */
@Generated(value = "by MybatisGenerator")
@Data
public class MybatisXmlEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date createTime;

    private Boolean isDeleted;

    private String name;

    private String enumField;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MybatisXmlEntity other = (MybatisXmlEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getEnumField() == null ? other.getEnumField() == null : this.getEnumField().equals(other.getEnumField()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getEnumField() == null) ? 0 : getEnumField().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", name=").append(name);
        sb.append(", enumField=").append(enumField);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}