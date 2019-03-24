package com.github.jactor.web

const val ENGLISH = "English"
const val NORWEGIAN = "Norwegian"
const val THAI = "Thai"
const val LANG = "lang"

data class Technology(
        val message: String,
        val tech: String,
        val url: String
)

data class SpringBeanNames(
        private val beans: MutableList<String> = ArrayList(),
        private val springBeans: MutableList<String> = ArrayList()
) {
    fun add(name: String) {
        if (name.contains(".spring")) {
            springBeans.add(name)
        } else {
            beans.add(name)
        }
    }

    fun listBeanNames(): List<String> {
        return beans
    }

    fun listSpringBeans(): List<String> {
        return springBeans
    }
}
