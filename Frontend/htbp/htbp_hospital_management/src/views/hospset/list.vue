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

    <div>
      <el-button type="danger" size="mini" @click="removeRows()"
        >Batch Delete</el-button
      >
    </div>
    <el-table
      :data="list"
      @selection-change="handleSelection"
      style="width: 100%"
    >
      <el-table-column type="selection" width="55" />
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
      <el-table-column label="Action" width="280" alige="center">
        <template slot-scope="scope">
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
          />
          <el-button
            type="primary"
            v-if="scope.row.status == 1"
            size="mini"
            @click="lockHostSet(scope.row.id, 0)"
            >Lock</el-button
          >
          <el-button
            type="danger"
            v-if="scope.row.status == 0"
            size="mini"
            @click="lockHostSet(scope.row.id, 1)"
            >Unlock</el-button
          >
          
          <router-link :to="'/hospSet/edit/'+scope.row.id"> 
             <el-button type="primary" size="mini" icon="el-icon-edit"/> 
          </router-link> 
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
      multipleSelection: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    lockHostSet(id, status) {
      hospset
        .lockHospSet(id, status)
        .then((response) => {
          this.getList(this.current);
        });
    },
    handleSelection(selection) {
      this.multipleSelection = selection;
    },
    removeRows() {
      this.$confirm(
        "This action will delete the record permanently, continue?",
        "Warning",
        {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      )
        .then(() => {
          var idList = [];
          for (var i = 0; i < this.multipleSelection.length; i++) {
            // var obj = this.multipleSelection[i]
            // var id = obj.id
            // idList.push(id)
            idList.push(this.multipleSelection[i].id);
          }
          hospset.batchRemove(idList).then((response) => {
            this.$message({
              type: "success",
              message: "Deleted!",
            });
            this.getList(this.current);
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "Canceled!",
          });
        });
    },
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
    removeDataById(id) {
      this.$confirm(
        "This action will delete the record permanently, continue?",
        "Warning",
        {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning",
        }
      )
        .then(() => {
          hospset.removeDataById(id).then((response) => {
            this.$message({
              type: "success",
              message: "Deleted!",
            });
            this.getList(this.current);
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "Canceled!",
          });
        });
    },
  },
};
</script>


