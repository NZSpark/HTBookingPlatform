<template> 
  <div class="app-container"> 
    Add hospital information 
    <br>
    <br>

    <el-form label-width="150px"> 
      <el-form-item label='Hosptial Name'> 
        <el-input v-model="hospitalSet.hosname"/>
      </el-form-item> 
         <el-form-item label='Hosptial Code'> 
            <el-input v-model="hospitalSet.hoscode"/> 
         </el-form-item> 
         <el-form-item label='API URL'> 
            <el-input v-model="hospitalSet.apiUrl"/> 
         </el-form-item> 
         <el-form-item label='Contacts Name'> 
            <el-input v-model="hospitalSet.contactsName"/> 
         </el-form-item> 
         <el-form-item label="Contacts Mobile"> 
            <el-input v-model="hospitalSet.contactsPhone"/> 
         </el-form-item> 
         <el-form-item> 
            <el-button type="primary" @click="saveOrUpdate()">Add</el-button> 
         </el-form-item> 
      </el-form> 
   </div> 
</template>

<script>
import hospset from "@/api/hospset";

export default {
  data() {
    return {
      hospitalSet: {},
    };
  },

  created() {
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id;

      this.getHostSet(id);
    }else{
      this.hospitalSet = {}
    }
  },

  methods: {
    saveOrUpdate() { 
     if(!this.hospitalSet.id) {
        this.save(); 
     } else {
        this.update() 
     } 
    } ,
    update() {
      hospset.updateHospSet(this.hospitalSet).then((response) => {
        this.$message({
          type: "success",
          message: "Updated!",
        });
        this.$router.push({ path: "/hospSet/list" });
      });
    },
    getHostSet(id) {
      hospset.getHospSet(id).then((response) => {
        this.hospitalSet = response.data;
      });
    },
    //add new hospital
    save() {
      hospset.saveHospset(this.hospitalSet).then((response) => {
        this.$message({
          type: "success",
          message: "Added!",
        });
        this.$router.push({ path: "/hospSet/list" });
      });
    },
  },
};
</script> 