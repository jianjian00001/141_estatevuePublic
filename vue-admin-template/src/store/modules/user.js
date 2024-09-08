import { login, loginOutApi, getInfo } from '@/api/user'
import { getToken, setToken, removeToken, setUserId, removeUserId, clearSession } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // user login  用户登录
  login({ commit }, userInfo) {
    //解构出用户信息
    const { username, password, userType } = userInfo
    return new Promise((resolve, reject) => {
      //调用api/user中的login方法
      login({ username: username.trim(), password: password, userType: userType }).then(response => {
        const { data } = response
        //设置token
        commit('SET_TOKEN', data.token)
        //设置到cookies里面
        setToken(data.token)
        //保存用户id
        setUserId(data.userId)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  //获取用户信息
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      //调用api/user.js里面的getInfo
      getInfo(state.token).then(response => {
        console.log('获取用户信息')
        console.log(response)
        const { data } = response

        if (!data) {
          return reject('获取用户信息失败，请登录!')
        }

        const { name, avatar, roles } = data
        if (!roles || roles.length <= 0) {
          reject('getInfo: 用户的权限信息必须是一个数组!')
        }
        //把权限字段放到sessionStorage里面
        sessionStorage.setItem('codeList', JSON.stringify(roles))
        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        commit('SET_ROLES', roles)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, dispatch, state }) {
    return new Promise((resolve, reject) => {
      loginOutApi(state.token).then(() => {
        //删除token
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        //清空用户
        removeUserId();
        //清空sessionStorage里面的所有内容
        clearSession();
        //清空tagsview里面的数据
        dispatch('tagsView/delAllViews', {}, { root: true })
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit, dispatch }) {
    return new Promise(resolve => {
      //删除token
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      //清空用户
      removeUserId();
      //清空sessionStorage里面的所有内容
      clearSession();
      //清空tagsview里面的数据
      dispatch('tagsView/delAllViews', {}, { root: true })
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

