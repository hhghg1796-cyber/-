package com.example

import com.example.domain.model.ContentType
import com.example.domain.model.ScannedPage
import com.example.domain.repository.MockLibraryRepository
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun repository_initialDocumentsAreLoaded() {
    val repo = MockLibraryRepository()
    val docs = repo.documents.value
    assertTrue("Should have initial documents", docs.isNotEmpty())
    val doc1 = repo.getDocumentById("doc_1")
    assertNotNull("doc_1 should exist", doc1)
    assertEquals("تاريخ الموصل الحضاري", doc1?.title)
  }

  @Test
  fun repository_searchByArabicKeyword() {
    val repo = MockLibraryRepository()
    val results = repo.search("الموصل")
    assertTrue("Should find results for الموصل", results.isNotEmpty())
  }

  @Test
  fun repository_toggleFavorite() {
    val repo = MockLibraryRepository()
    val initialStatus = repo.getDocumentById("doc_3")?.isFavorite ?: false
    repo.toggleFavorite("doc_3")
    val updatedStatus = repo.getDocumentById("doc_3")?.isFavorite
    assertEquals(!initialStatus, updatedStatus)
  }

  @Test
  fun repository_scannedPagesRotation() {
    val repo = MockLibraryRepository()
    val newPage = ScannedPage(id = "test_page_1", pageNumber = 1, rotationDegrees = 0)
    repo.addScannedPage(newPage)
    repo.rotateScannedPage("test_page_1")
    val rotated = repo.activeScanPages.value.find { it.id == "test_page_1" }
    assertEquals(90, rotated?.rotationDegrees)
  }

  @Test
  fun screens_bottomNavItemsAreNotNullAndHaveValidRoutes() {
    val items = com.example.ui.navigation.Screen.bottomNavItems
    assertNotNull("bottomNavItems should not be null", items)
    assertEquals(5, items.size)
    for (screen in items) {
      assertNotNull("Screen in bottomNavItems must not be null", screen)
      assertNotNull("Route must not be null", screen.route)
      assertFalse("Route must not be empty", screen.route.isEmpty())
      assertNotNull("Title Arabic must not be null", screen.titleArabic)
      assertNotNull("Icon must not be null", screen.icon)
    }
  }
}

