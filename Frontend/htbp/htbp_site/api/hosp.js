import request from '@/utils/request'

const api_name = `/api/hosp/hospital`
export default {
  //医院列表
  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/findHospList/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  getByHosname(hosname) {
    return request({
      url: `${api_name}/findByHosName/${hosname}`,
      method: 'get'
    })
  },
  show(hoscode) {
    return request({
      url: `${api_name}/findHospDetail/${hoscode}`,
      method: 'get'
    })
  },
  findDepartment(hoscode) {
    return request({
      url: `${api_name}/department/${hoscode}`,
      method: 'get'
    })
  }
}