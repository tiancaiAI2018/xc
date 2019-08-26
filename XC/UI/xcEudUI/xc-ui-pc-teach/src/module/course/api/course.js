import http from './../../../base/api/public'
import querystring from 'querystring'
let sysConfig = require('@/../config/sysConfig')
let apiUrl = sysConfig.xcApiUrlPre;

//查询课程列表
//我的课程列表
export const findCourseList = (page,size,params) => {
//使用工具类将json对象转成key/value
  let queries = querystring.stringify(params)
  return http.requestQuickGet(apiUrl+"/course/list/"+page+"/"+size+"?"+queries)
}

//查询课程分类
export const category_findlist= () => {
  return http.requestQuickGet(apiUrl+'/category/list')
}
/*添加课程基础信息*/
export const addCourseBase = params => {
  return http.requestPost(apiUrl+'/course',params)
}
/*查询课程计划*/
export const findTeachplanList = courseid => {
  return http.requestQuickGet(apiUrl+'/course/teachplan/'+courseid)
}
/*添加课程计划*/
export const addTeachplan = teachplah => {
  return http.requestPost(apiUrl+'/course/teachplan/',teachplah)
}

//保存课程图片地址到课程数据 库
export const addCoursePic= (courseId,pic) => {
  return http.requestPost(apiUrl+'/course/pic',pic)
}
//查询课程图片
export const findCoursePicList = courseId => {
  return http.requestQuickGet(apiUrl+'/course/pic/'+courseId)
}

//删除课程图片
export const deleteCoursePic= courseId => {
  return http.requestDelete(apiUrl+'/course/pic/'+courseId)
}
/*预览课程*/
export const preview = id => {
  return http.requestPost(apiUrl+'/course/preview/'+id);
}
/*发布课程*/
export const publish = id => {
  return http.requestPost(apiUrl+'/course/publish/'+id);
}
//查询课程信息
export const findCourseView = courseId => {
  return http.requestQuickGet(apiUrl+'/course/view/'+courseId)
}

/*保存媒资信息*/
export const savemedia = teachplanMedia => {
  return http.requestPost(apiUrl+'/course/savemedia',teachplanMedia);
}

export const getCoursebaseById = courseId => {
  return http.requestQuickGet(apiUrl+'/course/'+courseId)
}

export const getCourseMarketById = courseId => {
  return http.requestQuickGet(apiUrl+'/course/market/'+courseId)
}

export const updateCourseMarket = (courseId,market) => {
  return http.requestPut(apiUrl+'/course/market/'+courseId,market)
}
export const updateCoursebase = (courseId,course) => {
  return http.requestPut(apiUrl+'/course/'+courseId,course)
}

