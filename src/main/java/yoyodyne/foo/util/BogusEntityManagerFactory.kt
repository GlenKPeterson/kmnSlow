package yoyodyne.foo.util

import javax.persistence.Cache
import javax.persistence.EntityGraph
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.PersistenceUnitUtil
import javax.persistence.Query
import javax.persistence.SynchronizationType
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.metamodel.Metamodel

object BogusEntityManagerFactory: EntityManagerFactory {

    override fun getCriteriaBuilder(): CriteriaBuilder {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun getMetamodel(): Metamodel {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun getProperties(): MutableMap<String, Any> {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun <T : Any?> unwrap(cls: Class<T>?): T {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun createEntityManager(): EntityManager {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun createEntityManager(map: MutableMap<Any?, Any?>?): EntityManager {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun createEntityManager(synchronizationType: SynchronizationType?): EntityManager {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun createEntityManager(synchronizationType: SynchronizationType?, map: MutableMap<Any?, Any?>?): EntityManager {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun addNamedQuery(name: String?, query: Query?) {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun isOpen(): Boolean {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun getCache(): Cache {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun <T : Any?> addNamedEntityGraph(graphName: String?, entityGraph: EntityGraph<T>?) {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun getPersistenceUnitUtil(): PersistenceUnitUtil {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }

    override fun close() {
        throw UnsupportedOperationException("Tried to use Hibernate without providing a valid EntityManagerFactory")
    }
}