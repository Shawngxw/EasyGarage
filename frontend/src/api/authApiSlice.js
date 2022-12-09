import {api} from "./api";

export const authApiSlice = api.injectEndpoints({
    endpoints: builder => ({
        login: builder.mutation({
            query: credentials => ({
                url: '/login',
                method: 'POST',
                body: {...credentials}
            }),
            transformResponse: (response, meta, arg) => {
                console.log(response)
                return {data: response.data}
            },
        }),
    })
})

export const {
    useLoginMutation
} = authApiSlice