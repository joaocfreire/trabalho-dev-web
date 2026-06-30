import { test, expect } from '@playwright/test';
import { mockLoginInvalido, mockLoginSucesso } from './helpers/api-mocks';

test.describe('Página de Login', () => {
  test.beforeEach(async ({ page }) => {
    await page.goto('/login');
  });

  test('deve exibir o formulário de login', async ({ page }) => {
    await expect(page.getByRole('heading', { name: 'Página de Login' })).toBeVisible();
    await expect(page.locator('#email')).toBeVisible();
    await expect(page.locator('#senha')).toBeVisible();
    await expect(page.getByRole('button', { name: /Entrar/i })).toBeVisible();
  });

  test('deve validar email inválido no cliente', async ({ page }) => {
    await page.locator('#email').fill('email-invalido');
    await page.locator('#senha').fill('123456');
    await page.getByRole('button', { name: /Entrar/i }).click();

    await expect(page.getByText('Informe um email válido.')).toBeVisible();
  });

  test('deve validar senha vazia no cliente', async ({ page }) => {
    await page.locator('#email').fill('usuario@test.com');
    await page.getByRole('button', { name: /Entrar/i }).click();

    await expect(page.getByText('Informe a senha.')).toBeVisible();
  });

  test('deve exibir erro ao tentar logar com credenciais inválidas', async ({ page }) => {
    await mockLoginInvalido(page);

    await page.locator('#email').fill('usuario@errado.com');
    await page.locator('#senha').fill('senha_errada');
    await page.getByRole('button', { name: /Entrar/i }).click();

    const errorMessage = page.locator('.alert-danger');
    await expect(errorMessage).toBeVisible();
    await expect(errorMessage).toContainText('Email ou senha inválidos.');
  });

  test('deve redirecionar para home após login bem-sucedido', async ({ page }) => {
    await mockLoginSucesso(page);

    await page.locator('#email').fill('admin@test.com');
    await page.locator('#senha').fill('senha123');
    await page.getByRole('button', { name: /Entrar/i }).click();

    await expect(page).toHaveURL('/');
    await expect(page.getByText('Olá, Usuario Teste')).toBeVisible();
  });

  test('deve exibir link para cadastro de usuário', async ({ page }) => {
    await expect(page.getByRole('link', { name: 'Não tenho conta' })).toBeVisible();
    await page.getByRole('link', { name: 'Não tenho conta' }).click();
    await expect(page).toHaveURL('/cadastrar-usuario');
  });
});
