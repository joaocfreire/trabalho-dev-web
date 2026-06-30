import { test, expect } from '@playwright/test';
import { mockAlunos, mockLoginSucesso, mockTurmas } from './helpers/api-mocks';

test.describe('Navegação', () => {
  test.beforeEach(async ({ page }) => {
    await mockLoginSucesso(page);
    await mockAlunos(page);
    await mockTurmas(page);

    await page.goto('/login');
    await page.locator('#email').fill('admin@test.com');
    await page.locator('#senha').fill('senha123');
    await page.getByRole('button', { name: /Entrar/i }).click();
    await expect(page).toHaveURL('/');
  });

  test('deve exibir links principais na barra de navegação', async ({ page }) => {
    await expect(page.getByRole('link', { name: 'Home' })).toBeVisible();
    await expect(page.locator('a.nav-link[href="/listar-alunos"]')).toBeVisible();
    await expect(page.locator('a.nav-link[href="/listar-turmas"]')).toBeVisible();
    await expect(page.locator('a.nav-link[href="/listar-alunos-por-turma"]')).toBeVisible();
    await expect(page.locator('a.nav-link[href="/cadastrar-aluno"]')).toBeVisible();
  });

  test('deve navegar para a página de listagem de alunos', async ({ page }) => {
    await page.locator('a.nav-link[href="/listar-alunos"]').click();
    await expect(page).toHaveURL('/listar-alunos');
    await expect(page.getByRole('heading', { name: 'Lista de Alunos' })).toBeVisible();
  });

  test('deve navegar para a página de cadastro de aluno', async ({ page }) => {
    await page.locator('a.nav-link[href="/cadastrar-aluno"]').click();
    await expect(page).toHaveURL('/cadastrar-aluno');
    await expect(page.getByRole('heading', { name: 'Cadastro de Alunos' })).toBeVisible();
  });

  test('deve exibir opção de sair quando logado', async ({ page }) => {
    await expect(page.getByRole('link', { name: /Sair/i })).toBeVisible();
  });
});
