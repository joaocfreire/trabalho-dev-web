import { test, expect } from '@playwright/test';
import { mockAlunos } from './helpers/api-mocks';

test.describe('Listagem de Alunos', () => {
  test.beforeEach(async ({ page }) => {
    await mockAlunos(page);
    await page.goto('/listar-alunos');
  });

  test('deve exibir a tabela com os alunos retornados pela API', async ({ page }) => {
    await expect(page.getByRole('heading', { name: 'Lista de Alunos' })).toBeVisible();
    await expect(page.getByText('Ana Costa')).toBeVisible();
    await expect(page.getByText('ana@email.com')).toBeVisible();
    await expect(page.getByText('Bruno Lima')).toBeVisible();
    await expect(page.getByText('bruno@email.com')).toBeVisible();
  });

  test('deve exibir mensagem de carregamento enquanto busca dados', async ({ page }) => {
    await page.route('http://localhost:8080/alunos**', async (route) => {
      await new Promise((resolve) => setTimeout(resolve, 500));
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify([]),
      });
    });

    await page.goto('/listar-alunos');
    await expect(page.getByText('Recuperando alunos...')).toBeVisible();
  });
});
