<template>
      <div class="content">
     <!-- 标题 -->
    <div class="page-header">
      <h1 class="title">关系查询 <small>根据导演与演员的关系查询</small></h1>
    </div>

        <!-- 选择框 -->
        <div class="block">
          <span class="demonstration"
            >导演-演员查询：查询某导演经常合作的演员</span
          >
          <el-input v-model="ActorByD" placeholder="请输入导演"></el-input>
          <el-button @click="searchActorByD">查询</el-button>
        </div>
        <div class="block">
          <span class="demonstration"
            >演员-演员查询：查询某演员经常合作的演员</span
          >
          <el-input v-model="ActorByA" placeholder="请输入演员"></el-input>
          <el-button @click="searchActorByA">查询</el-button>
        </div>
        <div class="block">
          <span class="demonstration"
            >演员-导演查询：查询某演员经常合作的导演</span
          >
          <el-input v-model="DirectorByA" placeholder="请输入演员"></el-input>
          <el-button @click="searchDirectorByA">查询</el-button>
        </div>

        <div class="block">
          <span class="demonstration">最少合作次数</span>
          <el-input v-model="count1" placeholder="请输入最小合作次数"></el-input>
        </div>
        <el-card class="box-card" width="400px">
          <div slot="header">
            <span>查询用时： {{ usingTime1 }}</span>
          </div>
          <div>
            <span>详情：</span>
          </div>
          <div>
            <el-table class="dataTable" ref="multipleTable" :data="result1">
              <el-table-column label="合作者" prop="name"></el-table-column>
              <el-table-column label="合作次数" prop="count"></el-table-column>
          </el-table>
          </div>
        </el-card>




        <div class="block">
          <span class="demonstration"
            >电影类型</span
          >
          <el-input v-model="catalog" placeholder="请输入电影类型"></el-input>
          <el-button @click="searchDAcomment">查询此类型电影最受关注的导演-演员</el-button>
          <el-button @click="searchAAcomment">查询此类型电影最受关注的演员-演员</el-button>
        </div>
        <el-card class="box-card" width="600px">
          <div slot="header">
            <span>查询用时： {{ usingTime2 }}</span>
          </div>
          <div>
            <span>详情：</span>
          </div>
          <div>
            <el-table class="dataTable" ref="multipleTable" :data="result2">
              <el-table-column :label="this.label1" prop="name1"></el-table-column>
              <el-table-column :label="this.label2" prop="name2"></el-table-column>
              <el-table-column label="评论数" prop="count"></el-table-column>
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
      total1: '',
      total2: '',
      result1: [],
      result2: [],
      usingTime1: '',
      usingTime2: '',
      ActorByA: '',
      ActorByD: '',
      DirectorByA: '',
      DirectorByD: '',
      count1: 2,
      count2: 10,
      label1: '',
      label2: '',
      catalog:'',
    }
  },
  methods: {
    searchActorByD () {
      if(!this.count1){
        this.count1 = 0;
      }
      this.result1 = [];
      var startTime1 = new Date();
       axios.get('http://localhost:7078/coop/ActorsByDirector?director='+ this.ActorByD + '&count=' + this.count1)
      .then(response => {
        this.usingTime1 = new Date() - startTime1 + 'ms';
        response.data.forEach(element => {


          var coop = new Object();
          coop.name = element.actor1;
          coop.count = element.count;
          this.result1.push(coop);
        });});
    },
    searchActorByA () {
      if(!this.count1){
        this.count1 = 0;
      }
      this.result1 = [];
      var startTime1 = new Date();
       axios.get('http://localhost:7078/coop/ActorsByActor?actor='+ this.ActorByA + '&count=' + this.count1)
      .then(response => {
        this.usingTime1 = new Date() - startTime1 + 'ms';
        response.data.forEach(element => {
          var coop = new Object();
          coop.name = element.actor1;
          coop.count = element.count;
          this.result1.push(coop);
        });});
    },
    searchDirectorByA () {
      if(!this.count1){
        this.count1 = 0;
      }
      this.result1 = [];
      var startTime1 = new Date();
       axios.get('http://localhost:7078/coop/DirectorsByActor?actor='+ this.DirectorByA + '&count=' + this.count1)
      .then(response => {
        this.usingTime1 = new Date() - startTime1 + 'ms';
        response.data.forEach(element => {
          var coop = new Object();
          coop.name = element.director1;
          coop.count = element.count;
          this.result1.push(coop);
        });});
    },
    searchDAcomment () {
      if(!this.count2){
        this.count2 = 0;
      }
      this.result2 = [];
      this.label1 = "导演";
      this.label2 = "演员";
      var startTime2 = new Date();
       axios.get('http://localhost:7078/coop/dacomment?catalog=' + this.catalog)
      .then(response => {
        this.usingTime2 = new Date() - startTime2 + 'ms';
        response.data.forEach(element => {
          var coop = new Object();
          coop.name1 = element.director1;
          coop.name2 = element.actor1;
          coop.count = element.count;
          this.result2.push(coop);
        });});
    },
    searchAAcomment () {
      if(!this.count2){
        this.count2 = 0;
      }
      this.result2 = [];
      this.label1 = "演员1";
      this.label2 = "演员2";
      var startTime2 = new Date();
       axios.get('http://localhost:7078/coop/aacomment?catalog=' + this.catalog)
      .then(response => {
        this.usingTime2 = new Date() - startTime2 + 'ms';
        response.data.forEach(element => {
          var coop = new Object();
          coop.name1 = element.actor1;
          coop.name2 = element.actor2;
          coop.count = element.count;
          this.result2.push(coop);
        });});
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
