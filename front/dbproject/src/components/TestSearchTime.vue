<template>
    <div class="content">
  
        <!-- 标题 -->
        <div class="page-header">
          <h1 class="title">时间查询 <small>根据年份、月份、季度查询</small></h1>
        </div>
        <el-row>
          <el-col :span="8" :offset="8">
            <!-- 选择框 -->
            <div class="block">
              <span class="searchmethod">根据年份查询</span>
              <el-date-picker v-model="valueYear" type="year" value-format="YYYY" placeholder="选择年">
              </el-date-picker>
              <el-button @click="searchYear">查询</el-button>
            </div>
            <div class="block">
              <span class="searchmethod">根据季度查询</span>
              <el-date-picker v-model="valueQuarterYear" type="year" value-format="YYYY" placeholder="选择年">
              </el-date-picker>
              <el-select v-model="valueQuarter" placeholder="选择季度">
                <el-option
                    v-for="(quarter, index) in quarters"
                    :key="index"
                    :label="quarter.label"
                    :value="quarter.value"
                ></el-option>
              </el-select>
              <el-button @click="searchQuarter">查询</el-button>
            </div>
            <div class="block">
              <span class="searchmethod">根据年月查询</span>
              <el-date-picker v-model="valueMonth" type="month" value-format="YYYY-MM" placeholder="选择月">
              </el-date-picker>
              <el-button @click="searchMonth">查询</el-button>
            </div>
            <div class="block">
              <span class="searchmethod">根据年月日查询</span>
              <el-date-picker v-model="valueDate" type="date" value-format="YYYY-MM-DD" placeholder="选择日">
              </el-date-picker>
              <el-button @click="searchDate">查询</el-button>
            </div>
            
          </el-col>
          <el-col :span="20" :offset="2">
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
          </el-col>
        </el-row>
  
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
          valueYear: '',
          valueQuarterYear:'',
          valueQuarter:'',
          valueMonth: '',
          valueDate: '',
          valueSpan: [],
          quarters: [
            { label: '第一季度', value: 1 },
            { label: '第二季度', value: 2 },
            { label: '第三季度', value: 3 },
            { label: '第四季度', value: 4 },
          ],
          chartInstance: null,
        }
      },
      mounted() {
        // 在这里初始化并渲染柱状图
        this.renderBarChart();
      },
      methods: {
        updateChart() {
    this.$forceUpdate();
  },
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
          console.log(year);
          console.log(month);
          console.log(day);
          let formattedDate = '';
          if (year && month && day) {
            const date = new Date(year, month - 1, day);
            const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
            formattedDate = date.toLocaleDateString('en-US', options);
          }
          return formattedDate;
        },
        async searchYear() {
          if (!this.valueYear) {
            return;
          }
  
          var startTime1 = new Date();
          axios.get('http://localhost:7078/movie/year?year=' + this.valueYear)
          .then(response => {
            this.usingTime1 = new Date() - startTime1;
            this.result = response.data;
  
            if (this.result.length === 0) {
              console.log('数据为空');
              this.total='0';
              this.result = []; // 重置结果数据
              return; // 返回，结束函数的执行
            }
            else{
              this.total=this.result.length;
            }         
          });
          var startTime2 = new Date();
          axios.get('http://localhost:7078/movie/year?year=' + this.valueYear)
          .then(response => {
            this.usingTime2 = new Date() - startTime2;
          });
          this.$forceUpdate();
          this.renderBarChart();
        },
        searchQuarter() {
          if (!this.valueQuarterYear || !this.valueQuarter) {
            return;
          }
  
          console.log(this.valueQuarterYear);
          console.log(this.valueQuarter);
  
          var startTime1 = new Date();
          axios.get('http://localhost:7078/movie/quarter?year=' + this.valueQuarterYear + '&quarter=' + this.valueQuarter)
          .then(response => {
            this.usingTime1 = new Date() - startTime1 + 'ms';
            this.result = response.data;
  
            if (this.result.length === 0) {
              console.log('数据为空');
              this.total='0';
              this.result = [];
              return;
            }
            else{
              this.total=this.result.length;
            }
          });

          var startTime2 = new Date();
          axios.get('http://localhost:7079/movie/quarter?year=' + this.valueQuarterYear + '&quarter=' + this.valueQuarter)
          .then(response => {
            this.usingTime2 = new Date() - startTime2 + 'ms';
          });
          this.$forceUpdate();
          this.renderBarChart();
        },
  
        searchMonth() {
          if (!this.valueMonth) {
            return;
          }
  
          var startTime1 = new Date();
          axios.get('http://localhost:7078/movie/month?month=' + this.valueMonth)
          .then(response => {
            this.usingTime1 = new Date() - startTime1 + 'ms';
            this.result = response.data;
  
            if (this.result.length === 0) {
              console.log('数据为空');
              this.total='0';
              this.result = [];
              return;
            }else{
              this.total=this.result.length;
            }
          });

          var startTime2 = new Date();
          axios.get('http://localhost:7079/movie/month?month=' + this.valueMonth)
          .then(response => {
            this.usingTime2 = new Date() - startTime2 + 'ms';
          });
          this.$forceUpdate();
          this.renderBarChart();
        },
  
        searchDate() {
          console.log(this.valueDate);
          if (!this.valueDate) {
            return;
          }
  
          var startTime1 = new Date();
          axios.get('http://localhost:7078/movie/date?date=' + this.valueDate)
          .then(response => {
            this.usingTime1 = new Date() - startTime1 + 'ms';
            this.result = response.data;
  
            if (this.result.length === 0) {
              console.log('数据为空');
              this.total='0';
              this.result = [];
              return;
            }else{
              this.total=this.result.length;
            }
          });

          var startTime2 = new Date();
          axios.get('http://localhost:7079/movie/date?date=' + this.valueDate)
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
      margin: 20px;
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
    