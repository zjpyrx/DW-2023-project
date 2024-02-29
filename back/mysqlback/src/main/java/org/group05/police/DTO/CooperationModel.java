package org.group05.police.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CooperationModel {
    @TableField(value = "actor1")
    private String actor1;

    @TableField(value = "actor2")
    private String actor2;

    @TableField(value = "director1")
    private String director1;

    @TableField(value = "director2")
    private String director2;

    @TableField(value = "count")
    private int count;

    public void setActor1(String actor1) {
        this.actor1 = actor1;
    }

    public void setActor2(String actor2) {
        this.actor2 = actor2;
    }

    public void setDirector1(String director1) {
        this.director1 = director1;
    }

    public void setDirector2(String director2) {
        this.director2 = director2;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getActor1() {
        return actor1;
    }

    public String getActor2() {
        return actor2;
    }

    public String getDirector1() {
        return director1;
    }

    public String getDirector2() {
        return director2;
    }
}
