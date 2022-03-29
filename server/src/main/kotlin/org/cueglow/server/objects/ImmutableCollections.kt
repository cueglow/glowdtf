package org.cueglow.server.objects

/**
 * Immutable wrapper around Mutable List
 *
 * Copied from https://stackoverflow.com/a/37936456
 * Supposedly non-copy with low performance overhead (https://stackoverflow.com/a/38002121)
 */
class ImmutableList<T>(private val inner:List<T>) : List<T> by inner

/**
 * Immutable wrapper around Mutable Map
 *
 * Copied from https://stackoverflow.com/a/37936456
 * Supposedly non-copy with low performance overhead (https://stackoverflow.com/a/38002121)
 */
class ImmutableMap<K, V>(private val inner: Map<K, V>): Map<K, V> by inner