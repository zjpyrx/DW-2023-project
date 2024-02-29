<template>
    <!------------ 电影名 -------------->
    <div class="content">
    <div class="page-header">
      <h1 class="title">电影名查询 <small>根据电影名查询</small></h1>
    </div>
    <div class="block">
        <span class="demonstration">产品查询-输入电影名查询所有版本</span>
        <el-input v-model="title" placeholder="请输入电影名"></el-input>>
    </div>

    <!------------ 导演名 -------------->
    <div class="page-header">
      <h1 class="title">导演查询 <small>根据导演查询</small></h1>
    </div>
    <div class="block">
        <span class="searchmethod">导演查询-输入导演查询所有导演电影</span>
        <el-input v-model="director" placeholder="请输入导演"></el-input>
    </div>

    <!------------ 演员名 -------------->
    <div class="page-header">
      <h1 class="title">演员查询 <small>根据演员查询</small></h1>
    </div>
    <div class="block">
        <span class="demonstration">演员查询-输入演员查询所有参演电影</span>
          <el-input v-model="actor" placeholder="请输入演员"></el-input>
    </div>

    <!------------ 电影分类 -------------->
    <div class="page-header">
      <h1 class="title">分类查询 <small>根据分类查询</small></h1>
    </div>
    <div class="block">
        <span class="searchmethod">分类查询-输入要查询的电影分类</span>
        <el-input v-model="catalog" placeholder="请输入分类"></el-input>
    </div>

    <!------------ 评论 -------------->
    <div class="page-header">
      <h1 class="title">评论查询 <small>根据评论与评分查询</small></h1>
    </div>
    <div class="block">
        <span class="demonstration">评分查询-输入要查询的评分下限</span>
        <el-input v-model="score" placeholder="请输入评分"></el-input>
    </div>
    <el-button @click="searchByMovieInfo">总查询测试</el-button>



    <el-card class="box-card">
        <div slot="header">
            <span>总数： {{ total }}</span>
        </div>
        <div slot="header">
            <span>统计查询用时： {{ usingTime2 }}</span>
        </div>
        <div slot="header">
            <span>详情示例用时： {{ usingTime1 }}</span>
        </div>
        <div>
            <span>详情：</span>
        </div>
        <div>
            <el-table class="dataTable" ref="multipleTable" :data="result">
              <el-table-column label="电影名" prop="title"></el-table-column>
              <el-table-column label="导演" prop="director"></el-table-column>
              <el-table-column label="演员" prop="actor"></el-table-column>
              <el-table-column label="上映时间">
                  <template v-slot="{ row }">
                    <span v-if="row.year && row.month && row.day">
                      {{ formatDate(row.year, row.month, row.day) }}
                    </span>
                    <span v-else>-</span>
                  </template>
              </el-table-column>
              <el-table-column label="时长" prop="runtime"></el-table-column>
              <el-table-column label="类别" prop="catalog"></el-table-column>
              <el-table-column label="评分" prop="score"></el-table-column>
          </el-table>
          </div>
        </el-card>



</div>
</template>
<script>
import axios from 'axios';
export default {
  data () {
    return {
      total: '',
      result: '',
      usingTime1: '',
      usingTime2: '',
      title: '',
      director: '',
      actor: '',
      catalog: '',
      score: '',
    }
  },
  methods: {
    formatDate(year, month, day) {
      let formattedDate = '';
      if (year) {
        if (month && day && month !== '0' && day !== '0') {
          const date = new Date(year, month - 1, day);
          const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
          formattedDate = date.toLocaleDateString('en-US', options);
        } else {
          formattedDate = year;
        }
      }
      return formattedDate;
    },
    searchByMovieInfo(){
        var startTime1 = new Date();
        axios.get('http://localhost:7078/movie/allInfo?title='+ this.title+'&director='+this.director+'&actor='+this.actor+'&catalog='+this.catalog+'&score='+this.score)
        .then(response => {
        this.usingTime1 = new Date() - startTime1 + 'ms';
        this.total=response.data.length;
        if (response.data.length > 20) {
          
          this.result = response.data.slice(0, 20);
        }
        else if(response.data.length == 0){
          console.log('数据为空');
          this.result = []; // 重置结果数据
          return; // 返回，结束函数的执行
        }
        else{
          this.result = response.data;
          console.log("总数")
          console.log(this.total);
        }
        
       
      });

      axios.get('http://localhost:7078/movie/countAllInfo?title='+ this.title+'&director='+this.director+'&actor='+this.actor+'&catalog='+this.catalog+'&score='+this.score)
        .then(response => {
        this.usingTime1 = new Date() - startTime1 + 'ms';
        this.total=response.data.length;   
        console.log("数了一下");
        console.log(this.total);
      });
    },
  },
}
</script>

<style scoped>
.page-header .title {
  text-align: center;
}
.text {
  font-size: 14px;
}
.item {
  margin-bottom: 18px;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: '';
}
.clearfix:after {
  clear: both;
}
.box-card {
    width: 100%;
}
.block {
  width:400px;
}
.searchmethod{
    display:block;
  }
.content {
    width: 80%;
    position: relative;
    box-shadow: 0px 0px 10px 2px rgba(123, 103, 75, 0.427);
    background-color: rgba(255, 255, 255, 0.616); 
    margin-bottom: 30px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    margin-top: 16px;
    padding: 16px;
  
    border-radius: 5px;
    box-shadow: #9a9a9a 0px 0px 6px;
    box-shadow: #777777 0px 0px 3px;
    border-top: #0051ff 3px solid;
    border-top: solid 3px transparent;
  }
</style>
