import Vue from 'vue';
import VueRouter from 'vue-router';
import login from '../views/login/index';
import admin from '../views/Admin/index';
import adminHome from '../views/Admin/home';
import studentManage from '../views/Admin/studentManage/index'
import addStudent from "@/views/Admin/studentManage/addStudent";
import studentList from "@/views/Admin/studentManage/studentList";
import editorStudent from "@/views/Admin/studentManage/editorStudent";
import teacherManage from "@/views/Admin/teacherManage/index"
import addTeacher from "@/views/Admin/teacherManage/addTeacher";
import editorTeacher from "@/views/Admin/teacherManage/editorTeacher";
import courseManage from "@/views/Admin/courseManage/index";
import addCourse from "@/views/Admin/courseManage/addCourse";
import teacher from "@/views/Teacher/index";
import queryStudent from "@/views/Admin/studentManage/queryStudent";
import queryTeacher from "@/views/Admin/teacherManage/queryTeacher";
import student from "@/views/Student/index";
import editorCourse from "@/views/Admin/courseManage/editorCourse";
import courseList from "@/views/Admin/courseManage/courseList";
import queryCourse from "@/views/Admin/courseManage/queryCourse";
import offerCourse from "@/views/Teacher/offerCourse";
import teacherHome from "@/views/Teacher/home";
import setCourse from "@/views/Teacher/setCourse";
import studentHome from "@/views/Student/home";
import myOfferCourse from "@/views/Teacher/myOfferCourse";
import CourseTeacherManage from "@/views/Admin/selectCourseManage/index";
import queryCourseTeacher from "@/views/Admin/selectCourseManage/queryCourseTeacher";
import studentSelectCourseManage from "@/views/Student/selectCourse/index";
import selectCourse from "@/views/Student/selectCourse/selectCourse";
import querySelectedCourse from "@/views/Student/selectCourse/querySelectedCourse";
import studentCourseGrade from "@/views/Student/courseGrade/index";
import queryCourseGrade from "@/views/Student/courseGrade/queryCourseGrade";
import queryGradeCourse from "@/views/Admin/gradeCourseManage/queryGradeCourse";
import editorGradeCourse from "@/views/Admin/gradeCourseManage/editorGradeCourse";
import teacherGradeCourseManage from "@/views/Teacher/teacherGradeCourseManage/index";
import teacherQueryGradeCourse from "@/views/Teacher/teacherGradeCourseManage/teacherQueryGradeCourse";
import teacherGradeCourseList from "@/views/Teacher/teacherGradeCourseManage/teacherGradeCourseList";
import teacherEditorGradeCourse from "@/views/Teacher/teacherGradeCourseManage/teacherEditorGradeCourse";
import updateInfo from "@/components/updateInfo";

Vue.use(VueRouter)

const routes = [
  {
    // front page
    path: '/',
    name: 'index',
    component: login,
    redirect: '/login'
  },
  {
    // login page
    path: '/login',
    name: 'login',
    component: login
  },
  {
    // admin router
    path: '/admin',
    name: 'admin',
    redirect: '/studentList',
    component: admin,
    meta: {requireAuth: true},
    children: [
      {
        path: '/studentManage',
        name: 'Student Administration',
        component: studentManage,
        meta: {requireAuth: true},
        children: [
          {
            path: '/addStudent',
            name: 'Add students',
            component: addStudent,
            meta: {requireAuth: true}
          },
          {
            path: '/studentList',
            name: 'Student List',
            component: studentList,
            meta: {requireAuth: true},
          },
          {
            path: '/editorStudent',
            name: 'Edit students' information',
            component: editorStudent,
            meta: {requireAuth: true}
          },
          {
            path: '/queryStudent',
            name: 'Search Students',
            component: queryStudent,
            meta: {requireAuth: true},
            children: [
              {
                path: '/queryStudent/studentList',
                component: studentList,
                meta: {requireAuth: true}
              }
            ]
          }
        ]
      },
      {
        path: '/teacherManage',
        name: 'Teacher Management',
        component: teacherManage,
        meta: {requireAuth: true},
        children: [
          {
            path: '/addTeacher',
            name: 'Add teachers',
            component: addTeacher,
            meta: {requireAuth: true}
          },
          {
            path: '/queryTeacher',
            name: 'Teacher List',
            component: queryTeacher,
            meta: {requireAuth: true},
            children: [
            ]
          },
          {
            path: '/editorTeacher',
            name: 'Edit teachers' information',
            component: editorTeacher,
            meta: {requireAuth: true}
          },
        ]
      },
      {
        path: '/courseManage',
        name: 'Course Management',
        component: courseManage,
        meta: {requireAuth: true},
        children: [
          {
            path: '/addCourse',
            name: 'Add courses',
            component: addCourse,
            meta: {requireAuth: true}
          },
          {
            path: '/queryCourse',
            name: 'Search courses',
            component: queryCourse,
            meta: {requireAuth: true},
            children: [
              {
                path: '/courseList',
                name: 'Course List',
                component: courseList,
                meta: {requireAuth: true}
              },
            ]
          },
          {
            path: '/editorCourse',
            name: 'Edit courses' information',
            component: editorCourse,
            meta: {requireAuth: true}
          },
        ]
      },
      {
        path: '/CourseTeacher',
        name: 'Course management',
        component: CourseTeacherManage,
        meta: {requireAuth: true},
        children: [
          {
            path: '/queryCourseTeacher',
            name: 'Course management',
            component: queryCourseTeacher,
            meta: {requireAuth: true},
          }
        ]
      },
      {
        name: 'Student Grade Administration',
        path: "/gradeCourseManage",
        component: studentManage,
        meta: {requireAuth: true},
        children: [
          {
            path: '/queryGradeCourse',
            name: 'Query Student Grade',
            component: queryGradeCourse,
            meta: {requireAuth: true},
          },
          {
            path: '/editorGradeCourse',
            name: 'Edit',
            component: editorGradeCourse,
            meta: {requireAuth: true}
          }
        ]
      }
    ]
  },
  {
    path: '/teacher',
    name: 'teacher',
    component: teacher,
    redirect: '/courseManage',
    meta: {requireAuth: true},
    children: [
      {
        path: '/updateInfo',
        name: 'Edit teacher information',
        component: updateInfo,
        meta: {requireAuth: true},
        children: [
          {
            path: '/updateInfoHome',
            name: 'Edit teacher information',
            component: updateInfo,
            meta: {requireAuth: true}
          }
        ]
      },
      {
        path: '/courseManage',
        name: 'Course configuration',
        meta: {requireAuth: true},
        component: setCourse,
        children: [
          {
            path: '/myOfferCourse',
            name: 'Opened courses',
            component: myOfferCourse,
            meta: {requireAuth: true}
          },
          {
            path: '/offerCourse',
            name: 'Opened courses',
            component: offerCourse,
            meta: {requireAuth: true}
          },
        ]
      },
      {
        name: 'Grade edited by teacher',
        path: '/teacherQueryGradeCourseManage',
        component: teacherGradeCourseManage,
        meta: {requireAuth: true},
        children: [
          {
            path: '/teacherQueryGradeCourseManage',
            name: 'Grade management',
            component: teacherQueryGradeCourse,
            meta: {requireAuth: true}
          },
          {
            path: '/teacherEditorGradeCourse',
            name: 'Grade edited by teacher',
            component: teacherEditorGradeCourse,
            meta: {requireAuth: true}
          }
        ]
      }
    ]
  },
  {
    path: '/student',
    name: 'student',
    component: student,
    redirect: '/studentSelectCourseManage',
    meta: {requireAuth: true},
    children: [

      {
        path: '/updateInfo',
        name: 'Student update information',
        component: updateInfo,
        meta: {requireAuth: true},
        children: [
          {
            path: '/updateInfoHome',
            name: 'Update student information',
            component: updateInfo,
            meta: {requireAuth: true}
          }
        ]
      },
      {
        path: '/studentSelectCourseManage',
        name: 'Select course administration',
        component: studentSelectCourseManage,
        meta: {requireAuth: true},
        children: [
          {
            path: '/studentSelectCourse',
            name: 'Select course',
            component: selectCourse,
            meta: {requireAuth: true}
          },
          {
            path: '/querySelectedCourse',
            name: 'Query selected course',
            component: querySelectedCourse,
            meta: {requireAuth: true}
          }
        ]
      },
      {
        path: '/courseGrade',
        name: 'Student grade administration',
        component: studentCourseGrade,
        meta: {requireAuth: true},
        children: [
          {
            path: '/queryCourseGrade',
            name: 'Query course grade',
            component: queryCourseGrade,
            meta: {requireAuth: true}
          },
        ]
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

/*
  session Configuration：
    1. token
    2. name
    3. type
    4. tid
    5. sid
    5. System Information info
 */
router.beforeEach((to, from, next) => {
  console.log(from.path + ' ====> ' + to.path)
  if (to.meta.requireAuth) { // Confirm that if this router need login accessibility
    if (sessionStorage.getItem("token") === 'true') { // Certify that if there is a token in local.
      next()
    } else {
      // Requires login, return to login page.
      next({
        path: '/login',
        query: {redirect: to.fullPath}
      })
    }
  } else {
    // Prefer no login, if you've logined, there'll turn to the first page.
    if(sessionStorage.getItem("token") === 'true'){
      console.log('Check configuration of Blocker')
      const t = sessionStorage.getItem("type")
      next('/' + t);
    }else{
      next();
    }
  }
});
