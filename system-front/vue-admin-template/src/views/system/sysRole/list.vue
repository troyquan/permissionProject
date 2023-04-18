<template>
    <div id="app-container">
        <!--查询表单-->
        <div class="search-div">
            <el-form label-width="70px" size="small">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="Name">
                            <el-input style="width: 100%" v-model="searchObj.roleName" placeholder="Role Name"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row style="display:flex">
                    <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchData()">Search</el-button>
                    <el-button icon="el-icon-refresh" size="mini" @click="resetData">Reset</el-button>
                </el-row>
            </el-form>
        </div>
        <div class="tools-div">
            <el-button type="success" icon="el-icon-plus" size="mini" @click="add">Add</el-button>
            <el-button class="btn-add" size="mini" @click="batchRemove()">Batch Remove</el-button>
        </div>


        <el-table v-loading="listLoading" :data="list" stripe border style="width: 100%;margin-top: 10px;"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" />
            <el-table-column label="Index" width="70" align="center">
                <template slot-scope="scope">
                    {{ (page - 1) * limit + scope.$index + 1 }}
                </template>
            </el-table-column>

            <el-table-column prop="roleName" label="Name" />
            <el-table-column prop="roleCode" label="Code" />
            <el-table-column prop="createTime" label="Created Date" width="160" />
            <el-table-column label="Action" width="200" align="center">
                <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" title="Edit" />
                    <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)"
                        title="Delete" />
                    <el-button type="warning" icon="el-icon-baseball" size="mini" @click="showAssignAuth(scope.row)"
                        title="Permission" />
                </template>
            </el-table-column>
        </el-table>



        <!-- 分页组件 -->
        <el-pagination :current-page="page" :total="total" :page-size="limit" style="padding: 30px 0; text-align: center;"
            layout="total, prev, pager, next, jumper" @current-change="fetchData" />

        <el-dialog title="Add/Edit" :visible.sync="dialogVisible" width="40%">
            <el-form ref="dataForm" :model="sysRole" label-width="150px" size="small" style="padding-right: 40px;">
                <el-form-item label="Role Name">
                    <el-input v-model="sysRole.roleName" />
                </el-form-item>
                <el-form-item label="Role Code">
                    <el-input v-model="sysRole.roleCode" />
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
import api from '@/api/system/role.js'

export default {
    data() {
        return {
            listLoading: true,//loading icon
            list: [], //role list
            total: 0,
            page: 1,
            limit: 6,
            searchObj: {},

            dialogVisible: false,//dialog default false

            sysRole: {},//封装添加表单数据
            selectValue: []//selection data list
        }
    },
    //before render
    created() {
        //call data
        this.fetchData()
    },
    methods: {
        showAssignAuth(row) {
            //redirect to assign role by router
            this.$router.push('/system/assignAuth?id=' + row.id + '&roleName=' + row.roleName);
        },
        //selection change 
        handleSelectionChange(selection) {
            this.selectValue = selection
            console.log(this.selectValue)
        },
        batchRemove() {
            if (this.selectValue.length == 0) {
                this.$message.warning('Please Select to remove!')
                return
            }
            this.$confirm('Confirm deleting Role?', 'Alert', {
                confirmButtonText: 'Yes, I confirm',
                cancelButtonText: 'Cancel',
                type: 'warning'
            }).then(() => { // promise
                var idList = []
                //get selection id, pass them to idList
                for (let i = 0; i < this.selectValue.length; i++) {
                    let id = this.selectValue[i].id;
                    idList.push(id)
                }
                api.batchRemove(idList)
                    .then(response => {
                        this.$message({
                            type: 'success',
                            message: 'Deleted Successfully!'
                        });
                        //refresh
                        this.fetchData()
                    });

            })
        },
        //update show data
        edit(id) {
            this.dialogVisible = true
            api.getRoleId(id).then(response => {
                this.sysRole = response.data //give the value to sysRole  to v-model
            })
        },
        //click confirm
        saveOrUpdate() {

            //if add or update
            if (!this.sysRole.id) {
                this.saveRole()
            } else {
                this.updateRole()
            }

        },
        updateRole() {
            api.updata(this.sysRole).then(response => {
                //alert
                this.$message({
                    type: 'success',
                    message: 'Successfully Saved!'
                });
                //close dialog
                this.dialogVisible = false
                //refresh
                this.fetchData()
            })
        },
        saveRole() {
            console.log(this.sysRole)
            api.saveRole(this.sysRole)
                .then(response => {
                    //alert
                    this.$message({
                        type: 'success',
                        message: 'Successfully Saved!'
                    });
                    //close dialog
                    this.dialogVisible = false
                    //refresh
                    this.fetchData()
                })
        },
        //click add to show dialog
        add() {
            this.dialogVisible = true
            this.sysRole = {}
        },
        removeDataById(id) {
            this.$confirm('Confirm deleting Role?', 'Alert', {
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
        resetData() {
            //remove form
            this.searchObj = {}
            //select all data
            this.fetchData()
        },
        //list
        fetchData(pageNum = 1) {
            //give pageNum
            this.page = pageNum
            //ajax
            api.getPageList(this.page, this.limit, this.searchObj)
                .then(response => {
                    this.listLoading = false
                    console.log(response)
                    //每页数据列表
                    this.list = response.data.records
                    //总记录数
                    this.total = response.data.total
                })


        }
    }
}
</script>