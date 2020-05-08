package com.marcio.workmanagerdemo

import android.content.SharedPreferences

class FakeSharedPreferences(
    private val currentPreferences: MutableMap<String, Any?> = mutableMapOf(),
    private val listeners: MutableSet<SharedPreferences.OnSharedPreferenceChangeListener> = mutableSetOf()
) : SharedPreferences {

    override fun contains(key: String) = currentPreferences.containsKey(key)

    override fun getBoolean(key: String, defValue: Boolean) =
        currentPreferences[key] as? Boolean ?: defValue

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        listeners.remove(listener)
    }

    override fun getInt(key: String, defValue: Int) = currentPreferences[key] as? Int ?: defValue

    override fun getAll(): MutableMap<String, *> = currentPreferences

    override fun edit(): SharedPreferences.Editor = FakeEditor()

    override fun getLong(key: String, defValue: Long) = currentPreferences[key] as? Long ?: defValue

    override fun getFloat(key: String, defValue: Float) =
        currentPreferences[key] as? Float ?: defValue

    @Suppress("UNCHECKED_CAST")
    override fun getStringSet(key: String, defValues: MutableSet<String>?) =
        currentPreferences[key] as? MutableSet<String> ?: defValues

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        listeners.add(listener)
    }

    override fun getString(key: String, defValue: String?) =
        currentPreferences[key] as? String ?: defValue

    inner class FakeEditor(
        private val changedPreferences: MutableMap<String, Any?> = mutableMapOf(),
        private val removedPreferences: MutableSet<String> = mutableSetOf(),
        private var clear: Boolean = false
    ) : SharedPreferences.Editor {

        override fun clear(): SharedPreferences.Editor {
            clear = true
            return this
        }

        override fun putLong(key: String, value: Long): SharedPreferences.Editor {
            changedPreferences[key] = value
            return this
        }

        override fun putInt(key: String, value: Int): SharedPreferences.Editor {
            changedPreferences[key] = value
            return this
        }

        override fun remove(key: String): SharedPreferences.Editor {
            removedPreferences.add(key)
            return this
        }

        override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
            changedPreferences[key] = value
            return this
        }

        override fun putStringSet(
            key: String,
            values: MutableSet<String>?
        ): SharedPreferences.Editor {
            changedPreferences[key] = values
            return this
        }

        override fun commit(): Boolean {

            var valuesAdded = false

            for (key in removedPreferences) {
                currentPreferences.remove(key)
                notifyListeners(key)
            }

            for (key in changedPreferences.keys) {
                valuesAdded = true
                currentPreferences[key] = changedPreferences[key]!!
                notifyListeners(key)
            }

            if (clear) {
                valuesAdded = false

                for (key in currentPreferences.keys) {
                    notifyListeners(key)
                }

                currentPreferences.clear()
            }

            return valuesAdded
        }

        override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
            changedPreferences[key] = value
            return this
        }

        override fun apply() {
            commit()
        }

        override fun putString(key: String, value: String?): SharedPreferences.Editor {
            changedPreferences[key] = value
            return this
        }

        private fun notifyListeners(key: String) {
            for (listener in listeners) {
                listener.onSharedPreferenceChanged(this@FakeSharedPreferences, key)
            }
        }
    }
}

