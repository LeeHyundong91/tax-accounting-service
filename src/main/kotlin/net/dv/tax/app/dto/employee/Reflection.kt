package net.dv.tax.app.dto.employee

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

// 리플렉션으로 소스객체의 모든 프로퍼티를 대상 객체의 해당 프로퍼티로 복사한다. (같은 프로퍼티끼리만 복사된다는 말)
fun <T : Any, R : Any> T.copyCommonPropertiesTo(target: R) {
    val sourceProperties = this::class.members.filterIsInstance<KProperty1<T, *>>()
    val targetProperties = target::class.members.filterIsInstance<KMutableProperty1<R, *>>()

    for (sourceProp in sourceProperties) {
        val targetProp = targetProperties.find { it.name == sourceProp.name && it.returnType == sourceProp.returnType }
        if (targetProp != null) {
            val srcValue = sourceProp.get(this)
            val tarValue = targetProp.get(target)
            targetProp.setter.call(target, srcValue)
        }
    }
}

fun <T : Any, R : Any> T.updateCommonPropertiesTo(target: R) {
    val sourceProperties = this::class.members.filterIsInstance<KProperty1<T, *>>()
    val targetProperties = target::class.members.filterIsInstance<KMutableProperty1<R, *>>()

    for (sourceProp in sourceProperties) {
        val targetProp = targetProperties.find { it.name == sourceProp.name && it.returnType == sourceProp.returnType }
        val srcValue = sourceProp.get(this)
        if (targetProp != null && srcValue != null) {
            targetProp.setter.call(target, srcValue)
        }
    }
}