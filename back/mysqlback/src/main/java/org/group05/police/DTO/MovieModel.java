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
public class MovieModel {
    @TableField("ID")
    private int ID;

    @TableField("director")
    private String director;

    @TableField("title")
    private String title;

    @TableField("runtime")
    private String runtime;


    @TableField("year")
    private String year;

    @TableField("month")
    private String month;

    @TableField("day")
    private String day;


    @TableField("actor")
    private String actor;

    @TableField("score")
    private double score;

    @TableField("catalog")
    private String catalog;

    public void printm(){

        System.out.println("ID: "+this.ID);
        System.out.println("director: "+this.director);
        System.out.println("title: "+this.title);
        System.out.println("runtime: "+this.runtime);
        System.out.println("year: "+this.year);
        System.out.println("actor): "+this.actor);
        System.out.println("score: "+this.score);
        System.out.println("catalog: "+this.catalog);
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setYear(String year) {
        this.year = year;
    }
    public void setMonth(String month){
        this.month=month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
}
