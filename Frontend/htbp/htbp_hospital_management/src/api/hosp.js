import request from '@/utils/request'

export default {
  //医院列表
  getPageList(current,limit,searchObj) {
    return request ({
      url: `/admin/hosp/hospital/list/${current}/${limit}`,
      method: 'get',
      params: searchObj  
    })
  },
  //查询dictCode查询下级数据字典
  findByDictCode(dictCode) {
    return request({
        url: `/admin/cmn/dict/findByDictCode/${dictCode}`,
        method: 'get'
      })
    },
  
  //根据id查询下级数据字典
  findByParentId(dictCode) {
    return request({
        url: `/admin/cmn/dict/findChildData/${dictCode}`,
        method: 'get'
      })
  },

  updateStatus(id,status) {
    return request({
        url: `/admin/hosp/hospital/updateHospStatus/${id}/${status}`,
        method: 'get'
      })
  },

  getHospById(id){
    return request({
      url: `/admin/hosp/hospital/showHospitalDetail/${id}`,
      method: 'get'
    })
  },
  getDeptByHoscode(hoscode){
    return request({
      url: `/admin/hosp/department/getDeptList/${hoscode}`,
      method: 'get'
    })
  },
  getScheduleRule(page, limit, hoscode, depcode){
    return request({
      url: `/admin/hosp/department/getScheduleRule/${page}/${limit}/${hoscode}/${depcode}`,
      method: 'get'
    })
  },
  getScheduleDetail(hoscode, depcode,workDate){
    return request({
      url: `/admin/hosp/department/getScheduleDetail/${hoscode}/${depcode}/${workDate}`,
      method: 'get'
    })
  }

}
