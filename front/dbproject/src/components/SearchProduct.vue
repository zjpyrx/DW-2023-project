<template>
    <!------------ 电影名 -------------->
    <div class="content">
    <div class="page-header">
      <h1 class="title">产品调查 <small>根据电影名查询相关产品</small></h1>
    </div>
    <div class="block">
        <span class="demonstration">产品查询-输入电影名查询所有相关产品</span>
        <el-input v-model="title" placeholder="请输入电影名"></el-input>>
    </div>
    <el-button @click="searchProduct">查询</el-button>

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
              <el-table-column label="ASIN" prop="asin"></el-table-column>
              <el-table-column label="类别" prop="myformat"></el-table-column>
              <el-table-column label="价格" prop="cost"></el-table-column>
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
      asin: '',
      myformat: '',
      cost:'',
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
    searchProduct(){
        var startTime1 = new Date();
        axios.get('http://localhost:7078/product/movieTitle?title='+ this.title)
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
        }
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
