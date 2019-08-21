package com.xuecheng.manage_course.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.CourseMapper;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import com.xuecheng.manage_course.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private TeachplanRepository teachplanRepository;
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public TeachplanNode findTeachplanList(String courseId) {
        if(StringUtils.isEmpty(courseId)) ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        return teachplanMapper.selectList(courseId);
    }
    @Transactional
    @Override
    public boolean saveTeachplan(Teachplan teachplan) {
        //判断参数是否合法
        if(teachplan==null||StringUtils.isEmpty(teachplan.getCourseid())||StringUtils.isEmpty(teachplan.getPname()))
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        //课程ID
        String courseid = teachplan.getCourseid();
        //父节点
        String parentid = teachplan.getParentid();
        //没有添加父节点默认为根节点
        if(StringUtils.isEmpty(parentid)){
            //得到根节点（parentId)
            parentid=getParentid(courseid);
            teachplan.setParentid(parentid);
        }
        //得到父节点信息
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        if(!optional.isPresent())ExceptionCast.cast(CourseCode.COURSE_TEACHPLAN_PARENTISNULL);
        Teachplan teachplanParent = optional.get();
        String parentGrade = teachplanParent.getGrade();
        //设置子节点的等级
        teachplan.setGrade("1".equals(parentGrade)?"2":"3");
        //没有状态默认是未发布
        if(StringUtils.isEmpty(teachplan.getStatus())) teachplan.setStatus("0");
        //保存
        teachplanRepository.save(teachplan);
        return true;
    }
    /**
     * 功能描述: 得到课程计划的根节点，没有怎创建一个跟节点，根节点的名字和课程名字相同
     * 〈〉
     * @Param: [courseId]
     * @Return: java.lang.String
     * @Author: Administrator
     * @Date: 2019/8/4 19:57
     */
    private String getParentid(String courseId){
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndAndParentid(courseId, "0");
        //如果该课程下没有根节点，则创建一个根节点
        if(teachplanList==null||teachplanList.isEmpty()){
            Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
            if(!optional.isPresent())ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEISNULL);
            CourseBase courseBase = optional.get();
            Teachplan teachplan = new Teachplan();
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setOrderby(1);
            teachplan.setPname(courseBase.getName());
            teachplan.setCourseid(courseId);
            teachplan.setStatus("0");//根节点不予许未发布
            teachplanRepository.save(teachplan);
            return teachplan.getId();
        }
        return teachplanList.get(0).getId();
    }
    @Override
    public QueryResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest) {
        if (courseListRequest == null) courseListRequest = new CourseListRequest();
        if (page <= 1) page = 1;
        if (size <= 0) size = 10;
        PageHelper.startPage(page, size);
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        List<CourseInfo> content = courseListPage.getResult();
        long total = courseListPage.getTotal();
        QueryResult<CourseInfo> courseInfoQueryResult = new QueryResult<>();
        courseInfoQueryResult.setList(content);
        courseInfoQueryResult.setTotal(total);
        return courseInfoQueryResult;
    }
    @Override
    @Transactional
    public String addCourse(CourseBase courseBase) {
        if (courseBase == null || StringUtils.isEmpty(courseBase.getName()))
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        //设置课程为未发布（制作中）
        courseBase.setStatus("202001");
        CourseBase save = courseBaseRepository.save(courseBase);
        return save.getId();
    }
    @Override
    public CourseBase getCourseBaseById(String courseId) {
        if(StringUtils.isEmpty(courseId))ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent()) ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEISNULL);
        return optional.get();
    }
    @Override
    @Transactional
    public boolean editCourseBase(String courseId, CourseBase courseBase) {
        if(courseBase==null)ExceptionCast.cast(CommonCode.INVALID_PARAM);
        CourseBase courseBaseOld = getCourseBaseById(courseId);
        courseBaseOld.setName(courseBase.getName());
        courseBaseOld.setMt(courseBase.getMt());
        courseBaseOld.setSt(courseBase.getSt());
        courseBaseOld.setGrade(courseBase.getGrade());
        courseBaseOld.setStudymodel(courseBase.getStudymodel());
        courseBaseOld.setUsers(courseBase.getUsers());
        courseBaseOld.setDescription(courseBase.getDescription());
        courseBaseRepository.save(courseBaseOld);
        return true;
    }
}
