import request from '@/utils/request'

export default {
     updateHospSet(hospitalSet) { 

    return request ({ 

      url: `/admin/hosp/hospitalSet/updateHospitalSet`, 

      method: 'post', 

      data: hospitalSet 

    }) 

  } ,
     getHospSet(id) { 

            return request ({ 
        
              url: `/admin/hosp/hospitalSet/getHospSet/${id}`, 
        
              method: 'get' 
        
            }) 
        
          },
    getHospSetList(current, limit, searchObj) {
        return request({
            url: `/admin/hosp/hospitalSet/findPageHospSet/${current}/${limit}`,
            method: 'post',
            data: searchObj
        })
    },
    removeDataById(id) {
        return request({
            url: `/admin/hosp/hospitalSet/${id}`,
            method: 'delete'
        })
    },

    batchRemove(idList) {
        return request({
            url: `/admin/hosp/hospitalSet/batchRemove`,
            method: 'delete',
            data: idList
        })
    },

    lockHospSet(id, status) {
        return request({
            url: `/admin/hosp/hospitalSet/lockHospitalSet/${id}/${status}`,
            method: 'put'
        })
    },

    saveHospset(hospitalSet) {
        return request({
            url: `/admin/hosp/hospitalSet/saveHospitalSet`,
            method: 'post',
            data: hospitalSet
        })
    }

}
