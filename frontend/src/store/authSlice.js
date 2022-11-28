import {createSlice} from "@reduxjs/toolkit"

const authSlice = createSlice({
	name: 'auth',
	initialState: () => {
		return JSON.parse(localStorage.getItem("auth")) || {}
	},
	reducers: {
		setCredentials: (state, action) => {
			const {username, accessToken} = action.payload
			localStorage.setItem("auth", JSON.stringify({user: username, token: accessToken}))
			state.user = username
			state.token = accessToken
		},
		logOut: (state, action) => {
			state.user = null
			state.token = null
		}
	},
})

export const {setCredentials, logOut} = authSlice.actions

export default authSlice.reducer

export const selectCurrentUser = (state) => state.auth.user
export const selectCurrentToken = (state) => state.auth.token