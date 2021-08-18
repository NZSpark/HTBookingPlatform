import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  },

  {
    path: '/hospSet',
    component: Layout,
    redirect: '/hospSet/list',
    name: 'Hospital Management',
    meta: { title: 'Hospital Management', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'list',
        name: 'Hospital Setting List',
        component: () => import('@/views/hospset/list'),
        meta: { title: 'Hospital Setting List', icon: 'table' }
      },
      {
        path: 'add',
        name: 'Add Hospital Information',
        component: () => import('@/views/hospset/add'),
        meta: { title: 'Add Hospital Information', icon: 'tree' }
      },
      {
        path: 'edit/:id',
        name: 'Update Hospital Information',
        component: () => import('@/views/hospset/add'),
        meta: { title: 'Update Hospital Information', noCache: true },
        hidden: true
      },
      {
        path: 'hosp/list',
        name: 'Hospital List',
        component: () => import('@/views/hosp/list'),
        meta: { title: 'Hostpital List', icon: 'table' }
      },
      {
        path: 'hosp/show/:id',
        name: 'Hospital Detail',
        component: () => import('@/views/hosp/show'),
        meta: { title: 'Hostpital Detail', noCache: true },
        hidden: true
      },
      {
        path: 'hosp/schedule/:hoscode',
        name: 'Schedule',
        component: () => import('@/views/hosp/schedule'),
        meta: { title: 'Schedule', noCache: true },
        hidden: true
      }



    ]
  },
  {
    path: '/cmn',
    component: Layout,
    redirect: '/cmn/list',
    name: 'Dictionary Management',
    meta: { title: 'Dictionary Management', icon: 'el-icon-s-help' },
    alwaysShow: true,
    children: [
      {
        path: 'list',
        name: 'Dictionary List',
        component: () => import('@/views/dict/list'),
        meta: { title: 'Dictionary List', icon: 'table' }
      },
      {
        path: 'add',
        name: 'Add Dictionary Information',
        component: () => import('@/views/dict/add'),
        meta: { title: 'Add Dictionary Information', icon: 'tree' }
      },
      {
        path: 'edit/:id',
        name: 'Update Dictionary Information',
        component: () => import('@/views/dict/add'),
        meta: { title: 'Update Dictionary Information', noCache: true },
        hidden: true
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    redirect: '/user/userInfo/list',
    name: 'userInfo',
    meta: { title: '用户管理', icon: 'table' },
    alwaysShow: true,
    children: [
      {
        path: 'userInfo/list',
        name: '用户列表',
        component: () => import('@/views/user/userInfo/list'),
        meta: { title: '用户列表', icon: 'table' }
      }
    ]
  },

  {
    path: '/form',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('@/views/form/index'),
        meta: { title: 'Form', icon: 'form' }
      }
    ]
  },

  {
    path: '/nested',
    component: Layout,
    redirect: '/nested/menu1',
    name: 'Nested',
    meta: {
      title: 'Nested',
      icon: 'nested'
    },
    children: [
      {
        path: 'menu1',
        component: () => import('@/views/nested/menu1/index'), // Parent router-view
        name: 'Menu1',
        meta: { title: 'Menu1' },
        children: [
          {
            path: 'menu1-1',
            component: () => import('@/views/nested/menu1/menu1-1'),
            name: 'Menu1-1',
            meta: { title: 'Menu1-1' }
          },
          {
            path: 'menu1-2',
            component: () => import('@/views/nested/menu1/menu1-2'),
            name: 'Menu1-2',
            meta: { title: 'Menu1-2' },
            children: [
              {
                path: 'menu1-2-1',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
                name: 'Menu1-2-1',
                meta: { title: 'Menu1-2-1' }
              },
              {
                path: 'menu1-2-2',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
                name: 'Menu1-2-2',
                meta: { title: 'Menu1-2-2' }
              }
            ]
          },
          {
            path: 'menu1-3',
            component: () => import('@/views/nested/menu1/menu1-3'),
            name: 'Menu1-3',
            meta: { title: 'Menu1-3' }
          }
        ]
      },
      {
        path: 'menu2',
        component: () => import('@/views/nested/menu2/index'),
        name: 'Menu2',
        meta: { title: 'menu2' }
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        meta: { title: 'External Link', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
