import {createSlice} from "@reduxjs/toolkit"

const authSlice = createSlice({
	name: 'auth',
	initialState: () => {
		return JSON.parse(localStorage.getItem("auth")) || {}
	},
	reducers: {
		setCredentials: (state, action) => {
			const {email} = action.payload
			localStorage.setItem("auth", JSON.stringify({user: email}))
			state.user = email
		},
		logOut: (state, action) => {
			state.user = null
		}
	},
})

export const {setCredentials, logOut} = authSlice.actions

export default authSlice.reducer

export const selectCurrentUser = (state) => state.auth.user