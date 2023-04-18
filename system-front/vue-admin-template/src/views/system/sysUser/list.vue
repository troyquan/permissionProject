<template>
  <div id="app-container">
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="8">
            <el-form-item label="Keywrod">
              <el-input style="width: 95%" v-model="searchObj.keyword" placeholder="Username/Name/Phone"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Time">
              <el-date-picker v-model="createTimes" type="datetimerange" range-separator="To"
                start-placeholder="Start Time" end-placeholder="End Time" value-format="yyyy-MM-dd HH:mm:ss"
                style="margin-right: 20px;width: 100%;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchData()">Search</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetData">Reset</el-button>
        </el-row>
      </el-form>
    </div>

    <el-dialog title="AssignRole" :visible.sync="dialogRoleVisible">
      <el-form label-width="100px">
        <el-form-item label="UserName">
          <el-input disabled :value="sysUser.username"></el-input>
        </el-form-item>

        <el-form-item label="Role List">
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">SelectAll</el-checkbox>
          <div style="margin: 15px 0;"></div>
          <el-checkbox-group v-model="userRoleIds" @change="handleCheckedChange">
            <el-checkbox v-for="role in allRoles" :key="role.id" :label="role.id">{{role.roleName}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="assignRole" size="small">Save</el-button>
        <el-button @click="dialogRoleVisible = false" size="small">Canacel</el-button>
      </div>
    </el-dialog>
    <!-- 工具条 -->
    <div class="tools-div">
      <el-button type="success" icon="el-icon-plus" size="mini" @click="add">Add</el-button>
    </div>

    <!-- 列表 -->
    <el-table v-loading="listLoading" :data="list" stripe border style="width: 100%;margin-top: 10px;">

      <el-table-column label="Index" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="username" label="User Name" width="180" />
      <el-table-column prop="name" label="Name" width="110" />
      <el-table-column prop="phone" label="Phone Num" />
      <el-table-column label="Status" width="80">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status === 1" @change="switchStatus(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="createTime" />

      <el-table-column label="Action" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" title="Edit" />
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)"
            title="Delete" />
          <el-button type="warning" icon="el-icon-baseball" size="mini" @click="showAssignRole(scope.row)" title="AssignRole" />
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 分页组件 -->
    <el-pagination :current-page="page" :total="total" :page-size="limit" style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper" @current-change="fetchData" />

    <el-dialog title="Add/Edit" :visible.sync="dialogVisible" width="40%">
      <el-form ref="dataForm" :model="sysUser" label-width="100px" size="small" style="padding-right: 40px;">
        <el-form-item label="User Name" prop="username">
          <el-input v-model="sysUser.username" />
        </el-form-item>
        <el-form-item v-if="!sysUser.id" label="Password" prop="password">
          <el-input v-model="sysUser.password" type="password" />
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input v-model="sysUser.name" />
        </el-form-item>
        <el-form-item label="Phone Num" prop="phone">
          <el-input v-model="sysUser.phone" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">Cancel</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
//import customized js
import api from '@/api/system/user.js'
import roleApi from '@/api/system/role.js'


export default {
  data() {
    return {
      listLoading: false,//loading icon
      list: [], //role list
      total: 0,
      page: 1,
      limit: 10,
      searchObj: {},

      createTimes: [],

      dialogVisible: false,//dialog default false

      sysUser: {},//封装添加表单数据
      // selectValue: []//selection data list

      dialogRoleVisible: false,
      allRoles: [], // 所有角色列表
      userRoleIds: [], // 用户的角色ID的列表
      isIndeterminate: false, // 是否是不确定的
      checkAll: false // 是否全选
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    //change user status
    switchStatus(row) {
      row.status = row.status === 1 ? 0 : 1
      api.updateStatus(row.id, row.status).then(response => {
        if (response.code) {
          this.$message.success(response.message || 'Status Updated!')
          this.fetchData()
        }
      })
    },
    removeDataById(id) {
      this.$confirm('Confirm deleting User?', 'Alert', {
        confirmButtonText: 'Yes, I confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => { // promise
        console.log(id)
        api.removeId(id)
          .then(response => {
            this.$message({
              type: 'success',
              message: 'Deleted Successfully!'
            });
            this.fetchData()
          });

      })
    },
    edit(id) {
      this.dialogVisible = true
      api.getUserId(id)
        .then(response => {
          this.sysUser = response.data
        })
    },
    //add or update user
    saveOrUpdate() {
      if (!this.sysUser.id) {
        this.save()
      } else {
        this.update()
      }
    },
    update() {
      api.update(this.sysUser)
        .then(response => {
          //提示
          this.$message.success('Successfully Updated')
          //close dialog
          this.dialogVisible = false
          //refresh
          this.fetchData()
        })
    },
    save() {
      api.saveUser(this.sysUser)
        .then(response => {
          //提示
          this.$message.success('Successfully Added')
          //close dialog
          this.dialogVisible = false
          //refresh
          this.fetchData()
        })
    },
    //add dialog
    add() {
      this.dialogVisible = true
      this.sysUser = {}
    },
    //list
    fetchData(page = 1) {
      this.page = page
      if (this.createTimes != '' && this.createTimes.length == 2) {
        this.searchObj.createTimeBegin = this.createTimes[0]
        this.searchObj.createTimeEnd = this.createTimes[1]
      }
      api.getPageList(this.page, this.limit, this.searchObj)
        .then(response => {
          this.list = response.data.records
          this.total = response.data.total
        })
    },
    resetData() {
      console.log('Reset Form')
      this.searchObj = {}
      this.createTimes = []
      this.fetchData()
    },
    //展示分配角色
    showAssignRole (row) {
      this.sysUser = row
      this.dialogRoleVisible = true
       roleApi.getRolesByUserId(row.id).then(response => {
        this.allRoles = response.data.allRoles
        this.userRoleIds = response.data.userRoleIds
        this.checkAll = this.userRoleIds.length===this.allRoles.length
        this.isIndeterminate = this.userRoleIds.length>0 && this.userRoleIds.length<this.allRoles.length
      })
    },

    /*
    全选勾选状态发生改变的监听
    */
    handleCheckAllChange (value) {// value 当前勾选状态true/false
      // 如果当前全选, userRoleIds就是所有角色id的数组, 否则是空数组
      this.userRoleIds = value ? this.allRoles.map(item => item.id) : []
      // 如果当前不是全选也不全不选时, 指定为false
      this.isIndeterminate = false
    },

    /*
    角色列表选中项发生改变的监听
    */
    handleCheckedChange (value) {
      const {userRoleIds, allRoles} = this
      this.checkAll = userRoleIds.length === allRoles.length && allRoles.length>0
      this.isIndeterminate = userRoleIds.length>0 && userRoleIds.length<allRoles.length
    },

    //分配角色
    assignRole () {
      let assginRoleVo = {
        userId: this.sysUser.id,
        roleIdList: this.userRoleIds
      }
      roleApi.assignRoles(assginRoleVo).then(response => {
        this.$message.success(response.message || '分配角色成功')
        this.dialogRoleVisible = false
        this.fetchData(this.page)
      })
    },
    
  
  }

}
</script>