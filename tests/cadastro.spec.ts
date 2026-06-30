import { test, expect } from '@playwright/test';
import { mockCadastroAluno, mockCadastroUsuario, mockLoginSucesso } from './helpers/api-mocks';

test.describe('Cadastro de Usuário', () => {
  test.beforeEach(async ({ page }) => {
    await mockCadastroUsuario(page);
    await page.goto('/cadastrar-usuario');
  });

  test('deve exibir o formulário de cadastro', async ({ page }) => {
    await expect(page.getByRole('heading', { name: 'Cadastro de Usuários' })).toBeVisible();
    await expect(page.locator('#nome')).toBeVisible();
    await expect(page.locator('#senha')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Cadastrar' })).toBeVisible();
  });

  test('deve validar campos obrigatórios', async ({ page }) => {
    await page.getByRole('button', { name: 'Cadastrar' }).click();

    await expect(page.getByText("O 'nome' deve ser informado.")).toBeVisible();
    await expect(page.getByText('O e-mail deve ser informado.').first()).toBeVisible();
    await expect(page).toHaveURL('/cadastrar-usuario');
  });

  test('deve redirecionar para login após cadastro bem-sucedido', async ({ page }) => {
    await page.locator('#nome').fill('Novo Usuario');
    await page.locator('#descricao').fill('novo@test.com');
    await page.locator('#senha').fill('senha123');
    await page.getByRole('button', { name: 'Cadastrar' }).click();

    await expect(page).toHaveURL('/login');
  });
});

test.describe('Cadastro de Aluno', () => {
  test.beforeEach(async ({ page }) => {
    await mockLoginSucesso(page);
    await mockCadastroAluno(page);

    await page.goto('/login');
    await page.locator('#email').fill('admin@test.com');
    await page.locator('#senha').fill('senha123');
    await page.getByRole('button', { name: /Entrar/i }).click();

    await page.goto('/cadastrar-aluno');
  });

  test('deve cadastrar aluno e redirecionar para página do aluno', async ({ page }) => {
    await page.locator('#nome').fill('Carlos Ribeiro');
    await page.locator('#descricao').fill('carlos@email.com');
    await page.getByRole('button', { name: 'Cadastrar' }).click();

    await expect(page).toHaveURL('/aluno/99');
  });

  test('deve validar nome com menos de 3 caracteres', async ({ page }) => {
    await page.locator('#nome').fill('Ab');
    await page.locator('#descricao').fill('ab@email.com');
    await page.getByRole('button', { name: 'Cadastrar' }).click();

    await expect(page.getByText("O 'nome' deve ter pelo menos 3 caracteres.")).toBeVisible();
  });
});
