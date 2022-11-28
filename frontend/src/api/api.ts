// Import the RTK Query methods from the React-specific entry point
import {BaseQueryApi} from '@reduxjs/toolkit/dist/query/baseQueryTypes'
import {createApi, FetchArgs, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {setCredentials} from "../store/authSlice";


const baseQuery = fetchBaseQuery({
    baseUrl: '/api',
    credentials: 'include',
    prepareHeaders: (headers, {getState}) => {
        // @ts-ignore
        const token = getState().auth.token
        if (token) {
            headers.set("authorization", `Bearer ${token}`)
        }
        return headers
    }
})

const baseQueryWithReauth = async (args: string | FetchArgs, api: BaseQueryApi, extraOptions: {}) => {
    let result = await baseQuery(args, api, extraOptions)

    // @ts-ignore
    if (result?.error?.originalStatus === 403) {
        // send refresh token to get new access token
        const refreshResult = await baseQuery('/refresh', api, extraOptions)
        if (refreshResult?.data) {
            // @ts-ignore
            const user = api.getState().auth.user
            // store the new token
            console.log(refreshResult.data)
            // @ts-ignore
            api.dispatch(setCredentials({...refreshResult.data, user}))
            // retry the original query with new access token
            result = await baseQuery(args, api, extraOptions)
            // @ts-ignore
        } else if ([401, 403].includes(refreshResult?.error?.originalStatus)) {
            api.dispatch(setCredentials({user: null, token: null}))
        }
    }


    return result
}

export const api = createApi({
    reducerPath: 'api',
    baseQuery: baseQueryWithReauth,
    endpoints: builder => ({})
})

// Export the auto-generated hook for the endpoint
// 使用方法：
// const [modify,{isLoading,isFetching,error}] = useModifyMutation()
// const {data, isFetching} = useGetQuery()
export const {} = api

// @ts-ignore
// export const selectPostsQuery = createSelector(selectPosts,res=>res.data)