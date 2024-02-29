import { createRouter, createWebHashHistory } from 'vue-router'



const routes = [
    {
        path: '/',
        component: () => import('../components/MainMenu.vue'),
        redirect: '/SearchTime',
        
        children: [
            {
                path: 'SearchTime',
                component: () => import('../components/SearchTime.vue')

            },
            {
                path: 'SearchMovie',
                component: () => import('../components/SearchMovie.vue')

            },
            {
                path: 'SearchCooperation',
                component: () => import('../components/SearchCooperation.vue')

            },
            {
                path: 'SearchProduct',
                component: () => import('../components/SearchProduct.vue')

            },
            {
                path: 'TestSearchTime',
                component: () => import('../components/TestSearchTime.vue')

            },
            {
                path: 'TestSearchMovie',
                component: () => import('../components/TestSearchMovie.vue')

            },
            {
                path: 'TestSearchCooperation',
                component: () => import('../components/TestSearchCooperation.vue')

            },
            {
                path: 'TestSearchProduct',
                component: () => import('../components/TestSearchProduct.vue')

            },
        ]
           


    },
    
]

const router = createRouter({
    //router格式，hash
    history: createWebHashHistory(),
    routes : routes
    

})

export default router;