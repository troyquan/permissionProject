import request from '@/utils/request'

//variable url
const api_name = '/admin/system/sysRole/'
export default {
    batchRemove(idList){
        return request({
            //interface
            url: `${api_name}/batchRemove`,
            method: 'delete',
            data:idList
        })
    },
    getRoleId(id) {
        return request({
            //interface
            url: `${api_name}/findById/${id}`,
            method: 'get'
        })
    },
    updata(role) {
        return request({
            //interface
            url: `${api_name}/update`,
            method: 'post',
            data: role //role as JSON transfer to backEnd
        })

    },
    //Role list
    getPageList(page, limit, searchObj) {
        return request({
            //interface
            url: `${api_name}/${page}/${limit}`,
            method: 'get',
            params: searchObj
        })
    },
    //delete
    removeId(id) {
        return request({
            //interface
            url: `${api_name}/remove/${id}`,
            method: 'delete'
        })
    },
    //save
    saveRole(role) {
        return request({
            //interface
            url: `${api_name}/save`,
            method: 'post',
            data: role
        })
    },
    getRolesByUserId(userId){
        return request({
            //interface
            url: `${api_name}/toAssign/${userId}`,
            method: 'get'
        })
    },
    assignRoles(assignRoleVo){
        return request({
            //interface
            url: `${api_name}/doAssign`,
            method: 'post',
            data:assignRoleVo
        })
    }
}


