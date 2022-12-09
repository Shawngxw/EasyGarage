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
    if (result?.error?.status > 400) {
        // @ts-ignore
        console.log(result?.error?.status)
        // send refresh token to get new access token
        // api.dispatch(setCredentials({email: null}))
    }


    return result
}

export const api = createApi({
    reducerPath: 'api',
    baseQuery: baseQueryWithReauth,
    tagTypes: ['Places', "User"],
    endpoints: builder => ({
        getAllPlaces: builder.query({
            query: () => "/places/floors",
            providesTags: ['Places'],
        }),
        getCurrentUserCars: builder.query({
            query: () => "/vehicles"
        }),
        beginPark: builder.mutation({
            query: ({create, vehicle_name, floor, number}) => ({
                url: "parkings/add",
                method: "POST",
                body: {create, vehicle_name, floor, number},
            }),
            invalidatesTags: ['Places'],
        }),
        getCurrentUser: builder.query({
            query: () => "/me",
            providesTags: ['User'],
        }),
        editCurrentUser: builder.mutation({
            query: ({original_email, new_email, name, password, description}) => ({
                url: "/me",
                method: "PUT",
                body: {original_email, new_email, name, password, description}
            }),
            invalidatesTags: ['User'],
        }),
        makeReserve: builder.mutation({
            query: ({status, floor, number, begin_time, end_time, vehicle_name}) => ({
                url: "/reservations/add",
                method: "POST",
                body: {status, floor, number, begin_time, end_time, vehicle_name}
            }),
            invalidatesTags: ['Places'],
        })
    })
})

// Export the auto-generated hook for the endpoint
// 使用方法：
// const [modify,{isLoading,isFetching,error}] = useModifyMutation()
// const {data, isFetching} = useGetQuery()
export const {useGetAllPlacesQuery, useGetCurrentUserCarsQuery, useBeginParkMutation, useGetCurrentUserQuery, useEditCurrentUserMutation, useMakeReserveMutation} = api

// @ts-ignore
// export const selectPostsQuery = createSelector(selectPosts,res=>res.data)