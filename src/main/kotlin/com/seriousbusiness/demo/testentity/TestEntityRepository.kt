package com.seriousbusiness.demo.testentity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TestEntityRepository : JpaRepository<TestEntity, UUID>