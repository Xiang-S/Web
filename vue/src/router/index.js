import VueRouter from "vue-router";

const router = new VueRouter({
    model:history,
    routes: [
        {
            path: '/',
            redirect: '/service/login'
        },
        {
            path: '/home',
            meta: {
                title: "首页",
                index: 0,
                requireAuth: true
            },
            component: () => import("@/pages/Home"),
            children: [
                {
                    path: 'repertory',
                    component:()=>import('@/pages/Repertory')
                }
            ]
        },
        {
            path: '/service',
            component: () => import("@/pages/Service"),
            children: [
                {
                    path: 'login',
                    meta: {
                        title: "登录",
                        index: 1,
                        requireAuth: false
                    },
                    component: () => import("@/pages/Login")
                },
                {

                    path: 'register',
                    meta: {
                        title: "注册",
                        index: 2,
                        requireAuth: false
                    },
                    component: () => import("@/pages/Register")
                }
            ],
        }
    ]
})
router.beforeEach((to, from, next) => {
    if(to.meta.title){
        document.title=to.meta.title
    }
    if(to.matched.some(record => record.meta.requireAuth)){
        let token = localStorage.getItem('token')
        if (!token) {
            next({
                path: '/service/login',
                query: { redirect: to.fullPath }
            })
        } else {
            next()
        }
    } else {
        next() // 确保一定要调用 next()
    }
})
export default router
