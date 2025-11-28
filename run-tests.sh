#!/bin/bash

echo "🧪 Staffinity Recruiting Service - Test Suite"
echo "=============================================="
echo ""

# Colors
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

UNIT_LOG=/tmp/unit-tests.log
CTX_LOG=/tmp/context-tests.log
INT_LOG=/tmp/integration-tests.log
ALL_LOG=/tmp/all-tests.log

# 1. Unit Tests
echo "📦 Step 1: Running Unit Tests..."
./gradlew test --tests "CreateVacancyServiceTest" --console=plain > "$UNIT_LOG" 2>&1
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Unit Tests PASSED${NC}"
else
    echo -e "${RED}❌ Unit Tests FAILED${NC}"
    echo "Check log: $UNIT_LOG"
    echo "--- tail $UNIT_LOG ---"
    tail -n 100 "$UNIT_LOG" || true
fi
echo ""

# 2. Context Load Test
echo "🏗️  Step 2: Testing Spring Boot Context..."
./gradlew test --tests "RecruitingApplicationTests" --console=plain > "$CTX_LOG" 2>&1
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Context Tests PASSED${NC}"
else
    echo -e "${RED}❌ Context Tests FAILED${NC}"
    echo "Check log: $CTX_LOG"
    echo "--- tail $CTX_LOG ---"
    tail -n 100 "$CTX_LOG" || true
fi
echo ""

# 3. Integration Tests
echo "🌐 Step 3: Running Integration Tests..."
./gradlew test --tests "VacancyControllerIntegrationTest" --console=plain > "$INT_LOG" 2>&1
if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Integration Tests PASSED${NC}"
else
    echo -e "${RED}❌ Integration Tests FAILED${NC}"
    echo "Check log: $INT_LOG"
    echo "--- tail $INT_LOG ---"
    tail -n 100 "$INT_LOG" || true
fi
echo ""

# 4. Full Test Suite
echo "🎯 Step 4: Running Full Test Suite..."
./gradlew clean test --console=plain > "$ALL_LOG" 2>&1
TEST_RESULT=$?
echo ""

# Summary
echo "=============================================="
echo "📊 Test Summary"
echo "=============================================="

if [ $TEST_RESULT -eq 0 ]; then
    echo -e "${GREEN}✅ ALL TESTS PASSED!${NC}"
    echo ""
    echo "Next steps:"
    echo "  1. Run the application: ./gradlew bootRun"
    echo "  2. Test endpoints: See TESTING_GUIDE.md"
else
    echo -e "${YELLOW}⚠️  Some tests failed${NC}"
    echo ""
    echo "Logs available at:"
    echo "  - Unit Tests: $UNIT_LOG"
    echo "  - Context Tests: $CTX_LOG"
    echo "  - Integration Tests: $INT_LOG"
    echo "  - All Tests: $ALL_LOG"
    echo ""
    echo "HTML Report:"
    echo "  file://$(pwd)/build/reports/tests/test/index.html"
    echo ""
    echo "--- tail $ALL_LOG ---"
    tail -n 100 "$ALL_LOG" || true
fi

echo ""
echo "=============================================="

exit $TEST_RESULT
