import request from '@/utils/request'

//variable url
const api_name = '/admin/system/sysUser/'

export default {
    //list
    getPageList(page, limit, searchObj) {
        return request({
            //interface
            url: `${api_name}/${page}/${limit}`,
            method: 'get',
            params: searchObj
        })
    },
    //add user
    saveUser(user) {
        return request({
            //interface
            url: `${api_name}/save`,
            method: 'post',
            data: user
        })
    },
    getUserId(id) {
        return request({
            //interface
            url: `${api_name}/getUser/${id}`,
            method: 'get'
        })
    },
    update(user) {
        return request({
            //interface
            url: `${api_name}/update`,
            method: 'post',
            data: user //role as JSON transfer to backEnd
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
    //update user status
    updateStatus(id,status){
        return request({
            //interface
            url: `${api_name}/updateStatus/${id}/${status}`,
            method: 'get'
        })
    }
}