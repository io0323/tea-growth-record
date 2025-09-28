// アラートを自動的に非表示にする
document.addEventListener('DOMContentLoaded', function() {
  // アラートを取得
  const alerts = document.querySelectorAll('.alert');
  
  // 各アラートに対して
  alerts.forEach(function(alert) {
    // 3秒後に非表示にする
    setTimeout(function() {
      alert.style.opacity = '0';
      setTimeout(function() {
        alert.remove();
      }, 300);
    }, 3000);
  });
});

// フォームのバリデーション
document.addEventListener('DOMContentLoaded', function() {
  // フォームを取得
  const forms = document.querySelectorAll('form');
  
  // 各フォームに対して
  forms.forEach(function(form) {
    // 送信時にバリデーションを実行
    form.addEventListener('submit', function(event) {
      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
      }
      form.classList.add('was-validated');
    });
  });
});

// テーブルのソート機能
document.addEventListener('DOMContentLoaded', function() {
  // テーブルを取得
  const tables = document.querySelectorAll('table');
  
  // 各テーブルに対して
  tables.forEach(function(table) {
    // ヘッダーを取得
    const headers = table.querySelectorAll('th');
    
    // 各ヘッダーに対して
    headers.forEach(function(header, index) {
      // クリックイベントを追加
      header.addEventListener('click', function() {
        // ソート方向を取得
        const direction = header.dataset.direction === 'asc' ? -1 : 1;
        header.dataset.direction = direction === 1 ? 'asc' : 'desc';
        
        // テーブルの行を取得
        const rows = Array.from(table.querySelectorAll('tbody tr'));
        
        // 行をソート
        rows.sort(function(a, b) {
          const aValue = a.cells[index].textContent.trim();
          const bValue = b.cells[index].textContent.trim();
          
          if (aValue < bValue) return -1 * direction;
          if (aValue > bValue) return 1 * direction;
          return 0;
        });
        
        // ソートした行をテーブルに追加
        const tbody = table.querySelector('tbody');
        tbody.innerHTML = '';
        rows.forEach(function(row) {
          tbody.appendChild(row);
        });
      });
    });
  });
}); 