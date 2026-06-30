import { Page } from '@playwright/test';

const API_BASE = 'http://localhost:8080';

export async function mockLoginSucesso(page: Page) {
  await page.route(`${API_BASE}/autenticacao/login`, async (route) => {
    await route.fulfill({
      status: 200,
      contentType: 'application/json',
      body: JSON.stringify({
        token: 'token-mock-jwt',
        idUsuario: 1,
        nome: 'Usuario Teste',
        role: 'ADMIN',
      }),
    });
  });
}

export async function mockLoginInvalido(page: Page) {
  await page.route(`${API_BASE}/autenticacao/login`, async (route) => {
    await route.fulfill({ status: 401 });
  });
}

export async function mockAlunos(page: Page, alunos = [
  { id: 1, nome: 'Ana Costa', email: 'ana@email.com' },
  { id: 2, nome: 'Bruno Lima', email: 'bruno@email.com' },
]) {
  await page.route(`${API_BASE}/alunos**`, async (route) => {
    if (route.request().method() === 'GET') {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify(alunos),
      });
    } else {
      await route.continue();
    }
  });
}

export async function mockTurmas(page: Page, turmas = [
  {
    id: 1,
    codigo: 'T01',
    ano: '2025',
    periodo: '1',
    disciplina: { id: 1, nome: 'Programação Web', cargaHoraria: 60 },
    professor: { id: 1, nome: 'Prof. Silva' },
  },
]) {
  await page.route(`${API_BASE}/turmas**`, async (route) => {
    await route.fulfill({
      status: 200,
      contentType: 'application/json',
      body: JSON.stringify(turmas),
    });
  });
}

export async function mockCadastroUsuario(page: Page, sucesso = true) {
  await page.route(`${API_BASE}/usuarios`, async (route) => {
    if (route.request().method() === 'POST') {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify(
          sucesso
            ? { valido: true, duplicado: false, mensagem: 'Usuário cadastrado com sucesso!' }
            : { valido: false, duplicado: true, mensagem: 'Email já cadastrado!' }
        ),
      });
    } else {
      await route.continue();
    }
  });
}

export async function mockCadastroAluno(page: Page) {
  await page.route(`${API_BASE}/alunos`, async (route) => {
    if (route.request().method() === 'POST') {
      const body = route.request().postDataJSON();
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify({ id: 99, nome: body.nome, email: body.email }),
      });
    } else {
      await route.continue();
    }
  });
}
