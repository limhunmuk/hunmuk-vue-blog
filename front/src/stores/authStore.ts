import { defineStore } from 'pinia'

interface AuthState {
    accessToken: string | null
    refreshToken: string | null
    member: LoginMember | null
}


interface LoginMember {
    id: number
    loginId: string
    name: string
    nickName: string
    memType: string
    joinDt: string
    phoneNo: string
    addr: string
    addrDtl: string
}

export const useAuthStore = defineStore('auth', {
    state: (): AuthState => ({
        accessToken: localStorage.getItem('accessToken'),
        refreshToken: localStorage.getItem('refreshToken'),
        member: localStorage.getItem('member')
    }),

    getters: {
        isLoggedIn: (state) => !!state.accessToken,
        getMember: (state) => {
            if (state.member) {
                return {
                    memId: state.member.id,
                    name: state.member.name,
                    nickName: state.member.nickName,
                    email: state.member.loginId,
                    phoneNo: state.member.phoneNo,
                    address: state.member.addr,
                    addressDetail: state.member.addrDtl,
                }
            }
            return null
        },

    },
    actions: {
        login(accessToken: string, refreshToken: string, member: LoginMember) {
            this.accessToken = accessToken
            this.refreshToken = refreshToken
            this.member = member

            localStorage.setItem('accessToken', accessToken)
            localStorage.setItem('refreshToken', refreshToken)
            localStorage.setItem('member', JSON.stringify(this.member))

        },

        logout() {
            this.accessToken = null
            this.refreshToken = null
            this.member = null
            localStorage.removeItem('accessToken')
            localStorage.removeItem('refreshToken')
            localStorage.removeItem('member')
        },
    },
})
