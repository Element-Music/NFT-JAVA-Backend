package com.Element.Music.Model.DAO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue
    protected Long id;

    @CreatedDate
    protected Date createTime;

    @LastModifiedDate
    protected Date updateTime;

    protected boolean deleted;

    public JSONObject toJsonObject() {
        String jsonString = JSON.toJSONString(this);
        JSONObject jb = JSONObject.parseObject(jsonString);
        return jb;
    }
}
