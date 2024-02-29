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
    <div class="chart-container">
  <canvas id="bar-chart" width="400" height="400"></canvas>
</div>
    <el-card class="box-card">
        <div slot="header">
            <span>总数： {{ total }}</span>
        </div>
        <div slot="header">
            <span>MySQL查询用时： {{ usingTime1 }}</span>
        </div>
        <div slot="header">
            <span>Hive查询用时： {{ usingTime2 }}</span>
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
import Chart from 'chart.js/auto'; 
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
  mounted() {
        // 在这里初始化并渲染柱状图
        this.renderBarChart();
  },
  methods: {
    renderBarChart() {
        // 获取 DOM 中的 div 元素，用于放置柱状图
        const ctx = document.getElementById('bar-chart').getContext('2d');

        if (this.chartInstance) {
            this.chartInstance.destroy();
        }

        // 使用 Chart.js 创建柱状图
        this.chartInstance = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['MySQL', 'Hive'],
            datasets: [{
            label: '时间使用情况',
            data: [parseInt(this.usingTime1), parseInt(this.usingTime2)], // 将字符串转为数字
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
            ],
            borderWidth: 1,
            }],
        },
        options: {
            responsive: true, // 自适应大小
            maintainAspectRatio: false, // 不保持宽高比
            scales: {
            y: {
                beginAtZero: true,
            },
            },
        },
    });
  },
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
        var startTime2 = new Date();
        axios.get('http://localhost:7078/product/movieTitle?title='+ this.title)
        .then(response => {
          this.usingTime2 = new Date() - startTime2 + 'ms';
        });
        this.$forceUpdate();
        this.renderBarChart();
    },
  },
}
</script>

<style scoped>
    .chart-container {
  width: 400px;
  height: 400px;
}
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
