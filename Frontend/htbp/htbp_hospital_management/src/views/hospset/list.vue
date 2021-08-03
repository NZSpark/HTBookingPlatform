<template>
  <div class="app-container">
    Hospital Name List:
    <br />
    <br />
    
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="searchObj.hosname" placeholder="Hospital Name" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchObj.hoscode" placeholder="Hospital Code" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getList()">Search</el-button>
        <el-button type="default" @click="resetData()">Reset</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="list" style="width: 100%">
      <el-table-column type="index" label="No. " width="50" />
      <el-table-column prop="hosname" label="Hospital Name" />
      <el-table-column prop="hoscode" label="Hospital Code" />
      <el-table-column prop="apiUrl" label="api url address" width="200" />
      <el-table-column prop="contactsName" label="Contacts Name" />
      <el-table-column prop="contactsPhone" label="Contacts Number" />
      <el-table-column label="Status" width="80">
        <template slot-scope="scope">
          {{ scope.row.status === 1 ? "Available" : "Invalid" }}
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :total="total"
      :current-page="current"
      :page-size="limit"
      @current-change="getList"
      layout="total,prev,pager,next,jumper"
      style="padding: 30px 0; text-align: center"
    />
  </div>
</template>

<script>
import hospset from "@/api/hospset";

export default {
  data() {
    return {
      current: 1,
      limit: 3,
      searchObj: {},
      list: [],
      total: 0,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList(page = 1) {
      this.current = page;
      hospset
        .getHospSetList(this.current, this.limit, this.searchObj)
        .then((response) => {
          this.list = response.data.records;
          this.total = response.data.total;
          // console.log(response)
        })
        .catch((error) => {
          console.log(error);
        });
    },
    resetData() {
      this.searchObj.hosname = "";
      this.searchObj.hoscode = "";
    },
  },
};
</script>


